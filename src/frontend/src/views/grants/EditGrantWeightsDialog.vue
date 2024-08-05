<template>
  <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
    <v-card class="dialog-card">
      <div class="header-container">
        <v-icon class="close-icon">mdi-clipboard-edit-outline</v-icon>
        <h2 class="title">Editar Pesos de Avaliação</h2>
        <span class="font-weight-bold" v-if="props.grant">ID Bolsa: {{ props.grant?.id }}</span>
      </div>
      <v-divider></v-divider>
      <v-card-text>
        <v-chip color="primary">Avaliação Curricular ({{ (props.grant?.curricularEvaluationWeight ?? 0) * 100 }}%)</v-chip>
        <v-slider v-model="slider1" thumb-label="always" color="primary" max="100" min="0" step="1"></v-slider>
        <v-chip color="primary">Exercício Prático ({{ (props.grant?.practicalExerciseWeight ?? 0) * 100 }}%)</v-chip>
        <v-slider v-model="slider2" thumb-label="always" color="primary" max="100" min="0" step="1"></v-slider>
        <v-chip color="primary">Entrevista ({{ (props.grant?.interviewWeight ?? 0) * 100 }}%)</v-chip>
        <v-slider v-model="slider3" thumb-label="always" color="primary" max="100" min="0" step="1"></v-slider>
        <NotificationAlert v-if="errorMessage" :message="errorMessage" type="error" :visible="true" @close="errorMessage = ''"/>
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
import { ref, watch, defineProps, defineEmits, computed } from 'vue'
import RemoteService from '@/services/RemoteService'
import GrantDto from '@/models/grants/GrantDto'
import NotificationAlert from '@/components/NotificationAlert.vue'

const props = defineProps<{
  grant: GrantDto | null
  visible: boolean
}>()
const emit = defineEmits(['close', 'weights-updated'])
const weights = ref<number[]>([])
const slider1 = ref<number>(0)
const slider2 = ref<number>(0)
const slider3 = ref<number>(0)
const errorMessage = ref<string>('')

const dialogVisible = ref(false)

watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal
    if (props.grant) {
      getEvaluation()
    }
  }
)

const isSum100 = computed(() => {
  const total = slider1.value + slider2.value + slider3.value
  return total === 100
})

const handleSave = async () => {
  if (isSum100.value) {
    await saveEvaluationWeights()
    closeDialog()
  } else {
    errorMessage.value = 'A soma do peso das componentes de avaliação tem de ser 100%'
  }
}

const saveEvaluationWeights = async () => {
  try {
    if (props.grant) {
      weights.value = [slider1.value, slider2.value, slider3.value]
      console.log('Weights:', weights.value)

      // create a copy of the grant and update the weights
      const newGrant = { ...props.grant }
      newGrant.curricularEvaluationWeight = weights.value[0] / 100
      newGrant.practicalExerciseWeight = weights.value[1] / 100
      newGrant.interviewWeight = weights.value[2] / 100


      await RemoteService.updateGrantEvaluationWeights(newGrant)
    }
    emit('weights-updated')
  } catch (error) {
    console.log('ERROR:', error)
  }
}

const closeDialog = () => {
  errorMessage.value = ''
  dialogVisible.value = false
  emit('close')
}

const getEvaluation = async () => {
  console.log('Grant:', props.grant)
  if (props.grant) {
    // set sliders values
    slider1.value = (props.grant.curricularEvaluationWeight ?? 0) * 100
    slider2.value = (props.grant.practicalExerciseWeight ?? 0) * 100
    slider3.value = (props.grant.interviewWeight ?? 0) * 100

    console.log('Grant com os weigth:', props.grant)
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
