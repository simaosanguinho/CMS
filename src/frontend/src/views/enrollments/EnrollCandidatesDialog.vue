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
          v-model="selectedCandidates"
        >
        </v-data-table>
        <v-card-actions class="justify-end">
          <v-btn text="Fechar" variant="plain" @click="closeDialog" class="mr-4"></v-btn>
          <v-btn color="primary" text="Guardar" variant="tonal" @click="handleSave"></v-btn>
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
    { title: 'Name', value: 'name' },
    { title: 'Ist ID', value: 'istID' },
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
      const response = await RemoteService.getUnenrolledCandidates(props.grant?.id ?? '')
      candidates.value = response
    } catch (error) {
      console.log(error)
    }
  }
  
  const handleSave = async () => {
    try {
      await Promise.all(selectedCandidates.value.map(candidate => enrollCandidate(candidate)))
      selectedCandidates.value = []
      emit('candidates-enrolled', selectedCandidates.value)
    } catch (error) {
      console.log(error)
    } finally {
      closeDialog()
    }
  }
  
  const enrollCandidate = async (candidateId: number) => {
    try {
      if(!candidateId) {
        return
      }
      await RemoteService.enrollCandidate(candidateId, props.grant?.id ?? '')
    } catch (error) {
      console.log(error)
    }
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
  