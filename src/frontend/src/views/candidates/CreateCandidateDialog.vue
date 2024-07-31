<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Candidato"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Candidato">
        <v-card-text>
          <v-text-field
            label="Nome*"
            required
            v-model="newCandidate.name"
            :error-messages="nameError"
            @input="validateName"
          ></v-text-field>

          <v-text-field
            label="Email*"
            required
            v-model="newCandidate.email"
            type="email"
            :error-messages="emailError"
            @input="validateEmail"
          ></v-text-field>

          <v-text-field
            label="IstID*"
            required
            v-model="newCandidate.istID"
            type="number"
            :error-messages="istIDError"
            @input="validateIstID"
          ></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="handleSave"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type CandidateDto from '@/models/candidates/CandidateDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const emit = defineEmits(['candidate-created'])

const newCandidate = ref<CandidateDto>({
  name: '',
  email: '',
  istID: ''
})

const emailError = ref<string>('')
const nameError = ref<string>('')
const istIDError = ref<string>('')

const validateEmail = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!newCandidate.value.email) {
    emailError.value = 'Email is required'
  } else if (!emailPattern.test(newCandidate.value.email)) {
    emailError.value = 'Invalid email address'
  } else {
    emailError.value = ''
  }
}

const validateName = () => {
  if (!newCandidate.value.name) {
    nameError.value = 'Name is required'
  } else {
    nameError.value = ''
  }
}

const validateIstID = () => {
  if (!newCandidate.value.istID) {
    istIDError.value = 'Ist ID is required'
  } else {
    istIDError.value = ''
  }
}

const handleSave = async () => {
  validateName()
  validateEmail()
  validateIstID()
  if (!nameError.value && !emailError.value && !istIDError.value) {
    await saveCandidate()
    dialog.value = false
  }
}

const saveCandidate = async () => {
  try {
    await RemoteService.createCandidate(newCandidate.value)
  } catch (error) {
    console.log(error.response.data.message)
  }

  console.log(newCandidate.value)
  newCandidate.value = {
    name: '',
    email: '',
    istID: ''
  }
  emit('candidate-created')
}
</script>
