<template>
    <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
      <v-card class="dialog-card">
        <div class="header-container">
          <v-icon class="close-icon">mdi-list-box</v-icon>
          <h2 class="title">Bolsas Inscrito</h2>
          <span class="font-weight-bold" v-if="props.candidate">ID Candidato: {{ props.candidate?.id }}</span>
        </div>
  
        <v-divider></v-divider>
  
        <div v-if="grants.length === 0" class="text-center pa-4 text-lg-h5 text-grey font-weight-bold">
          O Candidato não se encontra inscrito em nenhuma bolsa
        </div>
  
        <v-data-table
          v-else
          :headers="headers"
          :items="grants"
          :hover="true"
          class="text-left table-container"
          item-value="id"
        ></v-data-table>
  
        <v-card-actions class="justify-end">
          <v-btn text="Close" variant="plain" @click="closeDialog" class="mr-4"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup lang="ts">
  import { ref, watch, defineProps, defineEmits } from 'vue'
  import RemoteService from '@/services/RemoteService'
  import type GrantDto from '@/models/grants/GrantDto';
  import type CandidateDto from '@/models/candidates/CandidateDto';
  
  const props = defineProps<{ candidate: CandidateDto | null; visible: boolean }>()
  const emit = defineEmits(['close', 'candidates-enrolled'])
  const grants = ref<GrantDto[]>([])
  const dialogVisible = ref(props.visible)
  
  const headers = [
    { title: 'ID', value: 'id', key: 'id' },
    { title: 'Start Date', value: 'startDate', key: 'startDate' },
    { title: 'End Date', value: 'endDate', key: 'endDate' },
    { title: 'Monthly Income (€)', value: 'monthlyIncome', key: 'monthlyIncome' },
    { title: 'Vacancies', value: 'vacancy', key: 'vacancy' },
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
        await getCandidateEnrolledGrants()
      }
    }
  )
  
  async function getCandidateEnrolledGrants() {
    grants.value.splice(0, grants.value.length)
  
    if (props.candidate) {
      RemoteService.getEnrolledGrantsByCandidateId(props.candidate?.id ?? '').then((data) => {
        data.forEach((grant: GrantDto) => {
          console.log('Grant:', grant)
          grant.startDate = formatDate(grant.startDate)
          grant.endDate = formatDate(grant.endDate)
          grants.value.push(grant)
        })
      })
    }
  }
  
  const closeDialog = () => {
    dialogVisible.value = false
    emit('close')
  }
  
  const handleOutsideClick = () => {
    closeDialog()
  }

  const formatDate = (date: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  }
  return new Intl.DateTimeFormat('pt-PT', options).format(new Date(date))
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
  
  .no-grants-message {
    padding: 16px;
    text-align: center;
    color: #666;
  }
  </style>
  