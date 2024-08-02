<template>
    <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
      <v-card prepend-icon="mdi-account" title="Inscrever Candidatos">
        <v-divider></v-divider>
  
        <v-data-table
          :headers="headers"
          :items="candidates"
          :hover="true"
          class="text-left"
        >
        </v-data-table>
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
  import GrantDto from '@/models/grants/GrantDto'
  
  const props = defineProps<{ grant: GrantDto | null; visible: boolean }>()
  const emit = defineEmits(['close', 'candidates-enrolled'])
  const candidates = ref<CandidateDto[]>([])
  const dialogVisible = ref(props.visible)
  
  const headers = [
    { title: 'Name', value: 'name', key: 'name' },
    { title: 'Ist ID', value: 'istID', key: 'istID' },
    { title: 'Actions', value: 'actions', key: 'actions' }
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
    closeDialog()
  }
  
  const saveCandidate = async () => {
    try {
      console.log('Saving candidate')
    } catch (error) {
      console.log(error)
    }
    emit('candidates-enrolled')
  }
  
  const closeDialog = () => {
    dialogVisible.value = false
    emit('close')
  }
  
  const handleOutsideClick = () => {
    closeDialog()
  }
  
  </script>
  