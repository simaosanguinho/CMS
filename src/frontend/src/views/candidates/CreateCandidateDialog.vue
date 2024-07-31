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
            <v-text-field label="Nome*" required v-model="newCandidate.name"></v-text-field>
  
            <v-text-field label="Email*" required v-model="newCandidate.email"></v-text-field>

            <v-text-field label="IstID*" required v-model="newCandidate.istID"></v-text-field>
          </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions>
            <v-spacer></v-spacer>
  
            <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="
                dialog = false;
                saveCandidate()
              "
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
  
  const saveCandidate = async () => {

    await RemoteService.createCandidate(newCandidate.value)

    console.log(newCandidate.value)
    newCandidate.value = {
      name: '',
      email: '',
      istID: ''
    }
    emit('candidate-created')
  }
  </script>
  