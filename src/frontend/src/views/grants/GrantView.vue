<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Bolsas</h2>
    </v-col>
    <v-col cols="auto">
      <CreateGrantDialog @grant-created="getGrants" />
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
    :items="grants"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:[`item.startDate`]="{ item }">
      {{ formatDate(item.startDate) }}
    </template>
    <template v-slot:[`item.endDate`]="{ item }">
      {{ formatDate(item.endDate) }}
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="editGrant(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deleteGrant(item)">mdi-delete</v-icon>
    </template>
  </v-data-table>

  <EditGrantDialog
    :grant="selectedGrant"
    :visible="editDialogVisible"
    @close="editDialogVisible = false"
    @grant-updated="getGrants"
  />
</template>

<script setup lang="ts">
import { ref, Ref } from 'vue'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import CreateGrantDialog from '@/views/grants/CreateGrantDialog.vue'
import EditGrantDialog from '@/views/grants/EditGrantDialog.vue'

const search = ref('')

const headers = [
  { title: 'ID', value: 'id', key: 'id' },
  { title: 'Start Date', value: 'startDate', key: 'startDate' },
  { title: 'End Date', value: 'endDate', key: 'endDate' },
  { title: 'Monthly Income (€)', value: 'monthlyIncome', key: 'monthlyIncome' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

// Use Ref<GrantDto[]> for the grants array
const grants: Ref<GrantDto[]> = ref([]) // Correct type declaration
const editDialogVisible = ref(false)
const selectedGrant = ref<GrantDto | null>(null)

async function getGrants() {
  // Ensure the value is assigned correctly
  grants.value = await RemoteService.getGrants()
}

function editGrant(grant: GrantDto) {
  selectedGrant.value = grant
  editDialogVisible.value = true
}

function deleteGrant(grant: GrantDto) {
  RemoteService.deleteGrant(grant).then(() => {
    getGrants()
  })
}

const fuzzySearch = (value: string, search: string) => {
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

const formatDate = (date: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric', 
    month: '2-digit',
    day: '2-digit'
  }
  return new Intl.DateTimeFormat('pt-PT', options).format(new Date(date))
}

getGrants()
</script>
