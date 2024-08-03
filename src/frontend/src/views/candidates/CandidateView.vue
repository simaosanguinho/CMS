<template>
    <v-row align="center">
      <v-col>
        <h2 class="text-left ml-1">Listagem de Candidatos</h2>
      </v-col>
      <v-col cols="auto">
        <CreateCandidateDialog @candidate-created="getCandidates" />
      </v-col>
    </v-row>
  
    <v-text-field
      v-model="search"
      label="Search"
      prepend-inner-icon="mdi-magnify"
      variant="outlined"
      hide-details
      single-line
    ></v-text-field>
  
    <v-data-table
      :headers="headers"
      :items="candidates"
      :search="search"
      :custom-filter="fuzzySearch"
      :hover="true"
      class="text-left"
    > 
      <template v-slot:[`item.grants`]="{ item }">
        <v-icon @click="showGrants(item)" class="mr-2">mdi-list-box</v-icon>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon @click="editCandidate(item)" class="mr-2">mdi-pencil</v-icon>
        <v-icon @click="deleteCandidate(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  
    <EditCandidateDialog
      :candidate="selectedCandidate"
      :visible="editDialogVisible"
      @close="editDialogVisible = false"
      @candidate-updated="getCandidates"
    />

    <CandidateEnrollmentsDialog
      :candidate="selectedCandidate"
      :visible="enrolledGrantsDialogVisible"
      @close="enrolledGrantsDialogVisible = false"
      @candidates-enrolled="getCandidates"
    />
  </template>
  
  <script setup lang="ts">
  import { ref, reactive } from 'vue'
  import RemoteService from '@/services/RemoteService'
  import type CandidateDto from '@/models/candidates/CandidateDto'
  import CreateCandidateDialog from '@/views/candidates/CreateCandidateDialog.vue'
  import EditCandidateDialog from '@/views/candidates/EditCandidateDialog.vue'
  import CandidateEnrollmentsDialog from '@/views/enrollments/CandidateEnrollmentsDialog.vue'
  
  const search = ref('')
  const headers = [
    { title: 'ID', value: 'id', key: 'id' },
    { title: 'Name', value: 'name', key: 'name' },
    { title: 'Email', value: 'email', key: 'email' },
    { title: 'Ist ID', value: 'istID', key: 'istID' },
    { title: 'Bolsas Inscrito', value: 'grants', key: 'grants' },
    { title: 'Actions', value: 'actions', key: 'actions' },
  ]
  
  const candidates: CandidateDto[] = reactive([])
  const editDialogVisible = ref(false)
  const enrolledGrantsDialogVisible = ref(false)
  const selectedCandidate = ref<CandidateDto | null>(null)
  
  async function getCandidates() {
    candidates.splice(0, candidates.length)
    RemoteService.getCandidates().then((data) => {
      data.forEach((candidate: CandidateDto) => {
        candidates.push(candidate)
      })
    })
  }
  
  function editCandidate(candidate: CandidateDto) {
    console.log(editDialogVisible.value)
    selectedCandidate.value = candidate
    editDialogVisible.value = true
  }
  
  function deleteCandidate(candidate: CandidateDto) {
    RemoteService.deleteCandidate(candidate).then(() => {
      getCandidates()
    })
  }

  function showGrants(candidate: CandidateDto) {
    selectedCandidate.value = candidate
    console.log('Showing grants for candidate:', candidate)
    enrolledGrantsDialogVisible.value = true
  }
  
  const fuzzySearch = (value: string, search: string) => {
    // Regex to match any character in between the search characters
    let searchRegex = new RegExp(search.split('').join('.*'), 'i')
    return searchRegex.test(value)
  }
  
  getCandidates()
  </script>
  