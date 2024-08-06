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
      <v-divider></v-divider>
      <v-card-text>
        <v-chip color="primary"
          >Avaliação Curricular ({{
            (props.grant?.curricularEvaluationWeight ?? 0) * 100
          }}%)</v-chip
        >
        <v-slider
          v-model="slider1"
          thumb-label="always"
          color="primary"
          max="20"
          min="0"
        ></v-slider>
        <v-chip color="primary"
          >Exercício Prático ({{ (props.grant?.practicalExerciseWeight ?? 0) * 100 }}%)</v-chip
        >
        <v-slider
          v-model="slider2"
          thumb-label="always"
          color="primary"
          max="20"
          min="0"
        ></v-slider>
        <v-chip color="primary"
          >Entrevista ({{ (props.grant?.interviewWeight ?? 0) * 100 }}%)</v-chip
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
        <v-btn variant="plain" @click="closeDialog">Fechar</v-btn>
        <v-btn color="primary" variant="tonal" @click="handleSave">Guardar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type EnrollmentDto from '@/models/enrollments/EnrollmentDto'
import type grantDto from '@/models/grants/GrantDto'
import EvaluationDto from '@/models/evaluations/EvaluationDto'

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
const evaluationId = ref<string | null>(null)

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
      scores.value = [
        Math.round(slider1.value * 10) / 10,
        Math.round(slider2.value * 10) / 10,
        Math.round(slider3.value * 10) / 10
      ]

      const evaluation = new EvaluationDto({
        id: evaluationId.value ?? undefined,
        scores: scores.value
      })
      await RemoteService.evaluateCandidate(evaluation)
    }
    emit('candidate-evaluated')
  } catch (error) {
    console.log(error)
  }
}

const closeDialog = () => {
  dialogVisible.value = false
  emit('close')
}

const getEvaluation = async () => {
  if (props.enrollment && props.enrollment.id) {
    RemoteService.getEvaluationByEnrollmentId(props.enrollment.id ?? '').then(
      (data) => {
        evaluationId.value = data.id ?? null
        scores.value = data.scores ?? []

        // Set slider values
        slider1.value = scores.value[0] ?? 0
        slider2.value = scores.value[1] ?? 0
        slider3.value = scores.value[2] ?? 0
      }
    )
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
