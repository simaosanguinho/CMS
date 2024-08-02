<template>
  <v-container>
    <v-card class="elevation-0">
      <v-card-title class="pa-5">
        <v-row align="center">
          <v-btn @click="goBack" icon>
            <v-icon>mdi-arrow-left</v-icon>
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn @click.stop="editGrant(grant)" icon class="mr-2">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn @click="deleteGrant" icon>
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </v-row>
      </v-card-title>
    </v-card>
    <v-row v-if="loading" justify="center" align="center">
      <v-col>
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </v-col>
    </v-row>
    <div v-else>
      <v-col cols="12" class="pt-0">
        <v-card-title class="pt-0">
          <h3>Detalhes de Bolsa</h3>
        </v-card-title>
        <v-divider></v-divider>
        <v-card class="elevation-0">
          <v-card-subtitle class="pa-6">
            <v-row>
              <v-col>
                <div class="font-weight-black">ID</div>
                {{ grant.id }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Start</div>
                {{ formatDate(grant.startDate) }}
              </v-col>
              <v-col>
                <div class="font-weight-black">End</div>
                {{ formatDate(grant.endDate) }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Monthly Income (€)</div>
                {{ grant.monthlyIncome }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Vacancies</div>
                {{ grant.vacancy }}
              </v-col>
            </v-row>
          </v-card-subtitle>
          <v-divider></v-divider>
        </v-card>
      </v-col>
      <v-row>
        <v-col>
          <v-row align="center">
            <v-card-title class="pt-5">
              <v-card-title class="pt-5">
                <h4>Candidatos Inscritos</h4>
              </v-card-title>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-btn
              class="text-none font-weight-regular mr-6"
              prepend-icon="mdi-plus"
              :text="winnerButtonText"
              color="primary"
            ></v-btn>
          </v-row>
          <v-data-table :hover="true" class="text-left"></v-data-table>
        </v-col>
      </v-row>
    </div>
  </v-container>

  <EditGrantDialog
    :grant="grant"
    :visible="editDialogVisible"
    @close="editDialogVisible = false"
    @grant-updated="fetchGrant(grant.id)"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import EditGrantDialog from '@/views/grants/EditGrantDialog.vue'

const route = useRoute()
const router = useRouter()
const editDialogVisible = ref(false)
const grant = ref<GrantDto | null>(null)
const loading = ref(true)

const fetchGrant = async (id: string) => {
  try {
    grant.value = await RemoteService.getGrantById(id)
    console.log('Grant:', grant.value)
  } catch (error) {
    console.error('Failed to fetch grant details:', error)
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.back()
}

function editGrant(newGrant: GrantDto) {
  grant.value = newGrant
  console.log('Selected grant:', grant.value)
  editDialogVisible.value = true
}

const deleteGrant = async () => {
  if (grant.value) {
    try {
      await RemoteService.deleteGrant(grant.value)
      router.push({ name: 'grants' })
    } catch (error) {
      console.error('Failed to delete grant:', error)
    }
  }
}

const formatDate = (date: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  }
  return new Intl.DateTimeFormat('pt-PT', options).format(new Date(date))
}

const winnerButtonText = computed(() => {
  return grant.value && grant.value.vacancy > 1 ? 'Selecionar Vencedores' : 'Selecionar Vencedor'
})

onMounted(() => {
  const grantId = route.params.id as string
  fetchGrant(grantId)
})
</script>
