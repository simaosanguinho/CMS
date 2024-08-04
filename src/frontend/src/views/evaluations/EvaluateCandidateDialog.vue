<template>
  <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
    <v-card class="dialog-card">
      <div class="header-container">
        <v-icon class="close-icon">mdi-clipboard-edit-outline</v-icon>
        <h2 class="title">Avaliar Candidato</h2>
        <span class="font-weight-bold" v-if="props.enrollment"
          >Candidato: {{ props.enrollment?.candidateName }}</span
        >
      </div>
      <v-card-text>
        <span class="font-weight-bold"
          >Avaliação Curricular - {{ (props.grant?.curricularEvaluationWeight ?? 0) * 100 }} %</span
        >
        <v-slider
          v-model="slider1"
          thumb-label="always"
          color="primary"
          max="20"
          min="0"
        ></v-slider>
        <span class="font-weight-bold"
          >Exercício Prático 
          z- {{ (props.grant?.practicalExerciseWeight ?? 0) * 100 }}%</span
        >
        <v-slider
          v-model="slider2"
          thumb-label="always"
          color="primary"
          max="20"
          min="0"
        ></v-slider>
        <span class="font-weight-bold"
          >Entrevista - {{ (props.grant?.interviewWeight ?? 0) * 100 }}%</span
        >
        <v-slider
          v-model="slider3"
          thumb-label="always"
          color="primary"
          max="20"
          min="0"
        ></v-slider>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="plain" @click="closeDialog">Close</v-btn>
        <v-btn color="primary" variant="tonal" @click="handleSave">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type EnrollmentDto from '@/models/enrollments/EnrollmentDto'
import type grantDto from '@/models/grants/GrantDto'

const props = defineProps<{
  grant: grantDto | null
  enrollment: EnrollmentDto | null
  visible: boolean
}>()
const emit = defineEmits(['close', 'candidate-evaluated'])
const scores = ref<number[]>([])
const slider1 = ref<number>(0)
const slider2 = ref<number>(0)
const slider3 = ref<number>(0)

const dialogVisible = ref(false)

watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal
    if (props.enrollment && props.enrollment.id) {
      getEvaluation()
    }
  }
)

const handleSave = async () => {
  await saveEvaluation()
  closeDialog()
}

const saveEvaluation = async () => {
  try {
    if (props.enrollment && props.enrollment.id) {
      await RemoteService.evaluateCandidate(props.enrollment.id, scores.value)
    }
    emit('candidate-evaluated')
  } catch (error) {
    console.log('ERROR:', error)
  }
}

const closeDialog = () => {
  dialogVisible.value = false
  emit('close')
}

const getEvaluation = async () => {
  console.log('Grant:', props.grant)
  if (props.enrollment && props.enrollment.id) {
    const evaluation = RemoteService.getEvaluationByEnrollmentId(props.enrollment.id ?? '').then(
      (data) => {
        console.log('Evaluation:', data)
        scores.value = data.scores ?? []
      }
    )
    console.log('Evaluation:', evaluation)
  }
}

const handleOutsideClick = () => {
  closeDialog()
}
</script>

<style scoped>
.dialog-card {
  padding: 16px;
  overflow: hidden;
}

.header-container {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  position: relative;
}

.close-icon {
  cursor: pointer;
  margin-right: 16px;
}

.title {
  font-weight: bold;
  margin: 0;
  flex-grow: 1;
}

.table-container {
  margin: 16px;
  max-width: calc(100% - 32px);
}
</style>
