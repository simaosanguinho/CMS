<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="900">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Bolsa"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-briefcase" title="Nova Bolsa">
        <v-card-text>
          <v-row>
            <v-col cols="6">
              <v-card-title class="text-center justify-center">
                <h4>Start Date</h4>
              </v-card-title>
              <v-divider></v-divider>
              <v-date-picker
                v-model="newGrant.startDate"
                label="Start Date*"
                required
                :error-messages="startDateError"
                @input="validateStartDate"
                class="mt-3"
              ></v-date-picker>
            </v-col>

            <v-col cols="6">
              <v-card-title class="text-center justify-center">
                <h4>End Date</h4>
              </v-card-title>
              <v-divider></v-divider>
              <v-date-picker
                v-model="newGrant.endDate"
                label="End Date*"
                required
                :error-messages="endDateError"
                @input="validateEndDate"
                class="mt-3"
              ></v-date-picker>
            </v-col>
          </v-row>
          <v-row>
            <v-text-field
              label="Monthly Income (€)*"
              required
              v-model="newGrant.monthlyIncome"
              type="number"
              :error-messages="monthlyIncomeError"
              @input="validateMonthlyIncome"
              class="mx-6"
            ></v-text-field>

            <v-text-field
              label="Vacancy*"
              required
              v-model="newGrant.vacancy"
              type="number"
              :error-messages="vacancyError"
              @input="validateVacancy"
              class="mx-6"
            ></v-text-field>
          </v-row>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn color="primary" text="Save" variant="tonal" @click="handleSave"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type GrantDto from '@/models/Grants/GrantDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const emit = defineEmits(['grant-created'])

const newGrant = ref<GrantDto>({
  startDate: null,
  endDate: null,
  monthlyIncome: null
})

const startDateError = ref<string>('')
const endDateError = ref<string>('')
const monthlyIncomeError = ref<string>('')
const vacancyError = ref<string>('')

const validateStartDate = () => {
  if (!newGrant.value.startDate) {
    startDateError.value = 'Start date is required'
  } else {
    startDateError.value = ''
  }
}

const validateEndDate = () => {
  if (!newGrant.value.endDate) {
    endDateError.value = 'End date is required'
  } else {
    endDateError.value = ''
  }
}

const validateMonthlyIncome = () => {
  if (!newGrant.value.monthlyIncome) {
    monthlyIncomeError.value = 'Monthly Income is required'
  } else if (newGrant.value.monthlyIncome <= 0) {
    monthlyIncomeError.value = 'Monthly Income must be greater than 0'
  } else {
    monthlyIncomeError.value = ''
  }
}

const validateVacancy = () => {
  if (!newGrant.value.vacancy) {
    vacancyError.value = 'Vacancy is required'
  } else if (newGrant.value.vacancy <= 0) {
    vacancyError.value = 'Invalid Vacancy'
  } else {
    vacancyError.value = ''
  }
}

const convertToUTC = (date: string | null): string | null => {
  if (date) {
    const dateObj = new Date(date)
    return new Date(
      Date.UTC(dateObj.getFullYear(), dateObj.getMonth(), dateObj.getDate())
    ).toISOString()
  }
  return null
}

const handleSave = async () => {
  validateStartDate()
  validateEndDate()
  validateMonthlyIncome()
  validateVacancy()
  if (!startDateError.value && !endDateError.value && !monthlyIncomeError.value) {
    console.log('newGrant.value:', newGrant.value)
    await saveGrant()
    dialog.value = false
  }
}

const saveGrant = async () => {
  try {
    newGrant.value.startDate = convertToUTC(newGrant.value.startDate)
    newGrant.value.endDate = convertToUTC(newGrant.value.endDate)
    await RemoteService.createGrant(newGrant.value)
  } catch (error) {
    console.log((error as any).response.data.message)
  }

  newGrant.value = {
    startDate: null,
    endDate: null,
    monthlyIncome: null
  }
  emit('grant-created')
}
</script>
