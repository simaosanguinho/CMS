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
    class="text-left custom-data-table"
    :hover="true"
  >
    <template v-slot:item="{ item }">
      <tr @click="onRowClick(item)">
        <td>{{ item.id }}</td>
        <td>{{ formatDate(item.startDate) }}</td>
        <td>{{ formatDate(item.endDate) }}</td>
        <td>{{ item.monthlyIncome }}</td>
        <td>{{ item.vacancy }}</td>
        <td>{{ item.onGoing ? 'A decorrer' : 'Fechado' }}</td>
        <td>
          <v-icon @click.stop="editGrant(item)" class="mr-2">mdi-pencil</v-icon>
          <v-icon @click.stop="deleteGrant(item)">mdi-delete</v-icon>
        </td>
      </tr>
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
import { useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import CreateGrantDialog from '@/views/grants/CreateGrantDialog.vue'
import EditGrantDialog from '@/views/grants/EditGrantDialog.vue'

const search = ref('')
const router = useRouter()

const headers = [
  { title: 'ID', value: 'id', key: 'id' },
  { title: 'Start Date', value: 'startDate', key: 'startDate' },
  { title: 'End Date', value: 'endDate', key: 'endDate' },
  { title: 'Monthly Income (€)', value: 'monthlyIncome', key: 'monthlyIncome' },
  { title: 'Vacancies', value: 'vacancy', key: 'vacancy' },
  { title: 'Concurso', value: 'onGoing', key: 'onGoing' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

const grants: Ref<GrantDto[]> = ref([])
const editDialogVisible = ref(false)
const selectedGrant = ref<GrantDto | null>(null)

async function getGrants() {
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

function onRowClick(grant: GrantDto) {
  editDialogVisible.value = false
  router.push({ name: 'grant-details', params: { id: grant.id } })
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
