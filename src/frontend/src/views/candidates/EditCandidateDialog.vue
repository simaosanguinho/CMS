<template>
    <v-dialog v-model="dialogVisible" max-width="400">
      <v-card prepend-icon="mdi-account" title="Edit Candidate">
        <v-card-text>
          <v-text-field label="Nome*" required v-model="localCandidate.name"></v-text-field>
          <v-text-field 
            label="Email*" 
            required 
            v-model="localCandidate.email"
            :error-messages="emailError"
            @input="validateEmail"
          ></v-text-field>
          <v-text-field label="IstID*" required v-model="localCandidate.istID"></v-text-field>
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
  
  const props = defineProps<{ candidate: CandidateDto | null, visible: boolean }>()
  const emit = defineEmits(['close', 'candidate-updated'])
  
  const dialogVisible = ref(false)
  const localCandidate = ref<CandidateDto | null>(null)
  const emailError = ref<string>('')
  
  watch(() => props.visible, (newVal) => {
    dialogVisible.value = newVal
    if (newVal && props.candidate) {
      localCandidate.value = { ...props.candidate }
    }
  })
  
  const validateEmail = () => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (localCandidate.value?.email && !emailPattern.test(localCandidate.value.email)) {
      emailError.value = 'Invalid email address'
    } else {
      emailError.value = ''
    }
  }
  
  const handleSave = async () => {
    validateEmail()
    if (!emailError.value && localCandidate.value) {
      await saveCandidate()
      closeDialog()
    }
  }
  
  const saveCandidate = async () => {
    if (localCandidate.value) {
      await RemoteService.updateCandidate(localCandidate.value)
      emit('candidate-updated')
    }
  }
  
  const closeDialog = () => {
    emit('close')
  }
  </script>
  