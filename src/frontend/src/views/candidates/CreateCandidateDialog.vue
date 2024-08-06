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
            label="IST ID*"
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
    <NotificationAlert
      v-if="errorMessage"
      :message="errorMessage"
      type="error"
      :visible="true"
      @close="errorMessage = ''"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type CandidateDto from '@/models/candidates/CandidateDto'
import RemoteService from '@/services/RemoteService'
import NotificationAlert from '@/components/NotificationAlert.vue';

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
const errorMessage = ref<string>('')

const validateEmail = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!newCandidate.value.email) {
    emailError.value = 'Email é obrigatório'
  } else if (!emailPattern.test(newCandidate.value.email)) {
    emailError.value = 'Email inválido'
  } else {
    emailError.value = ''
  }
}

const validateName = () => {
  if (!newCandidate.value.name) {
    nameError.value = 'Nome é obrigatório'
  } else {
    nameError.value = ''
  }
}

const validateIstID = () => {
  if (!newCandidate.value.istID) {
    istIDError.value = 'IST ID é Inválido'
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
  }
}

const saveCandidate = async () => {
  try {
    await RemoteService.createCandidate(newCandidate.value)
  } catch (error) {
    errorMessage.value = error.response.data.message
    return
  }
  newCandidate.value = {
    name: '',
    email: '',
    istID: ''
    
  }
  dialog.value = false
  emit('candidate-created')
}
</script>
