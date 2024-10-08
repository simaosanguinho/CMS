<template>
    <v-dialog v-model="dialogVisible" max-width="400" @click:outside="handleOutsideClick">
    <v-card prepend-icon="mdi-account" title="Editar Candidato">
      <v-card-text>
        <v-text-field
          label="Nome*"
          required
          v-model="localCandidate.name"
          :error-messages="nameError"
          @input="validateName"
        ></v-text-field>
        <v-text-field
          label="Email*"
          required
          v-model="localCandidate.email"
          :error-messages="emailError"
          @input="validateEmail"
        ></v-text-field>
        <v-text-field
          label="IST ID*"
          required
          v-model="localCandidate.istID"
          :error-messages="istIDError"
          @input="validateIstID"
          type="number"
        ></v-text-field>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text="Fechar" variant="plain" @click="closeDialog"></v-btn>
        <v-btn color="primary" text="Guardar" variant="tonal" @click="handleSave"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <NotificationAlert
    v-if="errorMessage"
    :message="errorMessage"
    type="error"
    :visible="true"
    @close="errorMessage = ''"
  />
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidates/CandidateDto'
import NotificationAlert from '@/components/NotificationAlert.vue';

const props = defineProps<{ candidate: CandidateDto | null; visible: boolean }>()
const emit = defineEmits(['close', 'candidate-updated'])

const dialogVisible = ref(false)
const localCandidate = ref<CandidateDto | null>(null)
const emailError = ref<string>('')
const nameError = ref<string>('')
const istIDError = ref<string>('')
const errorMessage = ref<string>('')

watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal
    if (newVal && props.candidate) {
      localCandidate.value = { ...props.candidate }
    }
  }
)

const validateEmail = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!localCandidate.value.email) {
    emailError.value = 'Email é obrigatório'
  } else if (!emailPattern.test(localCandidate.value.email)) {
    emailError.value = 'Email inválido'
  } else {
    emailError.value = ''
  }
}

const validateName = () => {
  if (!localCandidate.value.name) {
    nameError.value = 'Nome é obrigatório'
  } else {
    nameError.value = ''
  }
}

const validateIstID = () => {
  if (!localCandidate.value.istID) {
    istIDError.value = 'IST ID é inválido'
  } else {
    istIDError.value = ''
  }
}

const handleSave = async () => {
  validateName()
  validateEmail()
  validateIstID()
  if (!emailError.value && !emailError.value && !istIDError.value) {
    await saveCandidate()
  }
}

const saveCandidate = async () => {
  try {
    await RemoteService.updateCandidate(localCandidate.value)
  } catch (error) {
    errorMessage.value = error.response.data.message
    return
  }

  localCandidate.value = {
    name: '',
    email: '',
    istID: ''
  }
  closeDialog()
  emit('candidate-updated')
}

const closeDialog = () => {
  emit('close')
}

const handleOutsideClick = () => {
  closeDialog()
}
</script>
