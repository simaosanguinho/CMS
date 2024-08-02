<template>
    <v-dialog v-model="dialogVisible" max-width="400">
    <v-card prepend-icon="mdi-account" title="Edit Candidate">
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
          label="IstID*"
          required
          v-model="localCandidate.istID"
          :error-messages="istIDError"
          @input="validateIstID"
        ></v-text-field>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text="Close" variant="plain" @click="closeDialog"></v-btn>
        <v-btn color="primary" text="Save" variant="tonal" @click="handleSave"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type CandidateDto from '@/models/candidates/CandidateDto'

const props = defineProps<{ candidate: CandidateDto | null; visible: boolean }>()
const emit = defineEmits(['close', 'candidate-updated'])

const dialogVisible = ref(false)
const localCandidate = ref<CandidateDto | null>(null)
const emailError = ref<string>('')
const nameError = ref<string>('')
const istIDError = ref<string>('')

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
    emailError.value = 'Email is required'
  } else if (!emailPattern.test(localCandidate.value.email)) {
    emailError.value = 'Invalid email address'
  } else {
    emailError.value = ''
  }
}

const validateName = () => {
  if (!localCandidate.value.name) {
    nameError.value = 'Name is required'
  } else {
    nameError.value = ''
  }
}

const validateIstID = () => {
  if (!localCandidate.value.istID) {
    istIDError.value = 'Ist ID is required'
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
    closeDialog()
  }
}

const saveCandidate = async () => {
  try {
    await RemoteService.updateCandidate(localCandidate.value)
  } catch (error) {
    console.log(error)
  }

  console.log(localCandidate.value)
  localCandidate.value = {
    name: '',
    email: '',
    istID: ''
  }
  emit('candidate-updated')
}

const closeDialog = () => {
  emit('close')
}
</script>
