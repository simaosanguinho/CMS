<template>
    <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
      <v-card class="dialog-card">
        <div class="header-container">
          <v-icon class="close-icon">mdi-account</v-icon>
          <h2 class="title">Inscrever Candidatos</h2>
          <span class="font-weight-bold" v-if="props.grant">Grant ID: {{ props.grant?.id }}</span>
        </div>
  
        <v-divider></v-divider>
  
        <v-data-table
          :headers="headers"
          :items="candidates"
          :hover="true"
          class="text-left table-container"
          show-select
          item-value="id"
          v-model:selected="selectedCandidates"
        >
        </v-data-table>
        <v-card-actions class="justify-end">
          <v-btn text="Close" variant="plain" @click="closeDialog" class="mr-4"></v-btn>
          <v-btn color="primary" text="Save" variant="tonal" @click="handleSave"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup lang="ts">
  import { ref, watch, defineProps, defineEmits } from 'vue'
  import RemoteService from '@/services/RemoteService'
  import type CandidateDto from '@/models/candidates/CandidateDto'
  import GrantDto from '@/models/grants/GrantDto'
  
  const props = defineProps<{ grant: GrantDto | null; visible: boolean }>()
  const emit = defineEmits(['close', 'candidates-enrolled'])
  const candidates = ref<CandidateDto[]>([])
  const selectedCandidates = ref<CandidateDto[]>([])
  const dialogVisible = ref(props.visible)
  
  const headers = [
    { title: 'Name', value: 'name', key: 'name' },
    { title: 'Ist ID', value: 'istID', key: 'istID' },
  ]
  
  watch(
    () => props.visible,
    (newVal) => {
      dialogVisible.value = newVal
    }
  )
  
  watch(
    dialogVisible,
    async (newVal) => {
      if (newVal) {
        await getUnenrolledCandidates()
      }
    }
  )
  
  async function getUnenrolledCandidates() {
    try {
      console.log('Getting unenrolled candidates', props.grant)
      const response = await RemoteService.getUnenrolledCandidates(props.grant?.id ?? '')
      candidates.value = response // Assuming response contains the list of candidates
    } catch (error) {
      console.log(error)
    }
  }
  
  const handleSave = async () => {
    console.log('Selected candidates', selectedCandidates.value)
    await saveCandidate()
    closeDialog()
  }
  
  const saveCandidate = async () => {
    try {
      console.log('Saving candidate')
      // Handle saving selected candidates
    } catch (error) {
      console.log(error)
    }
    emit('candidates-enrolled', selectedCandidates.value)
  }
  
  const closeDialog = () => {
    dialogVisible.value = false
    emit('close')
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
    margin-bottom: 8px; /* Space below the header */
    position: relative;
  }
  
  .close-icon {
    cursor: pointer;
    margin-right: 16px; /* Space between icon and title */
  }
  
  .title {
    font-weight: bold;
    margin: 0; /* Remove default margin */
    flex-grow: 1; /* Allow title to take available space */
  }
  
  .table-container {
    margin: 16px;
    max-width: calc(100% - 32px); /* Ensures table fits within dialog */
  }
  
  </style>
  