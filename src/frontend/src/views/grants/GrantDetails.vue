<template>
    <v-container>
      <!-- Loading Spinner -->
      <v-row v-if="loading" justify="center" align="center">
        <v-col>
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </v-col>
      </v-row>
      <v-row v-else>
        <v-col cols="12">
          <v-card>
            <v-card-title>
              <h3>Grant Details</h3>
            </v-card-title>
            <v-card-subtitle>
              <v-row>
                <v-col>
                  <strong>ID:</strong> {{ grant.id }}
                </v-col>
                <v-col>
                  <strong>Start Date:</strong> {{ formatDate(grant.startDate) }}
                </v-col>
                <v-col>
                  <strong>End Date:</strong> {{ formatDate(grant.endDate) }}
                </v-col>
                <v-col>
                  <strong>Monthly Income (€):</strong> {{ grant.monthlyIncome }}
                </v-col>
              </v-row>
            </v-card-subtitle>
            <v-card-actions>
              <v-btn @click="goBack">Back</v-btn>
              <v-btn @click="editGrant">Edit</v-btn>
              <v-btn @click="deleteGrant" color="red">Delete</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import RemoteService from '@/services/RemoteService'
  import type GrantDto from '@/models/grants/GrantDto'
  
  const route = useRoute()
  const router = useRouter()
  
  const grant = ref<GrantDto | null>(null)
  const loading = ref(true)
  
  const fetchGrant = async (id: string) => {
    try {
      grant.value = await RemoteService.getGrantById(id)
    } catch (error) {
      console.error('Failed to fetch grant details:', error)
    } finally {
      loading.value = false
    }
  }
  
  const goBack = () => {
    router.back()
  }
  
  const editGrant = () => {
    if (grant.value) {
      router.push({ name: 'EditGrant', params: { id: grant.value.id } })
    }
  }
  
  const deleteGrant = async () => {
    if (grant.value) {
      try {
        await RemoteService.deleteGrant(grant.value)
        router.push({ name: 'GrantsList' }) // Assuming 'GrantsList' is the name of the page listing grants
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
  
  onMounted(() => {
    const grantId = route.params.id as string
    fetchGrant(grantId)
  })
  </script>
  
  <style scoped>
  .v-card {
    margin: 20px;
  }
  .v-spinner {
    width: 100px;
    height: 100px;
  }
  </style>
  