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
import { ref, reactive } from 'vue'
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

const grants: GrantDto[] = reactive([])
const editDialogVisible = ref(false)
const selectedGrant = ref<GrantDto | null>(null)

async function getGrants() {
  grants.splice(0, grants.length)
  RemoteService.getGrants().then((data) => {
    data.forEach((grant: GrantDto) => {
      grants.push(grant)
    })
  })
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
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

getGrants()
</script>
