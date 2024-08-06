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
                <h4>Data de Início</h4>
              </v-card-title>
              <v-divider></v-divider>
              <v-date-picker
                v-model="startDate"
                label="Início*"
                required
                :error-messages="startDateError"
                @input="validateStartDate"
                class="mt-3"
              ></v-date-picker>
            </v-col>

            <v-col cols="6">
              <v-card-title class="text-center justify-center">
                <h4>Data de Fim</h4>
              </v-card-title>
              <v-divider></v-divider>
              <v-date-picker
                v-model="endDate"
                label="Fim*"
                required
                :error-messages="endDateError"
                @input="validateEndDate"
                class="mt-3"
              ></v-date-picker>
            </v-col>
          </v-row>
          <v-row>
            <v-text-field
              label="Valor Mensal (€)*"
              required
              v-model="newGrant.monthlyIncome"
              type="number"
              :error-messages="monthlyIncomeError"
              @input="validateMonthlyIncome"
              class="mx-6"
            ></v-text-field>

            <v-text-field
              label="Vagas*"
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

          <v-btn text="Fechar" variant="plain" @click="dialog = false"></v-btn>

          <v-btn color="primary" text="Guardar" variant="tonal" @click="handleSave"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <NotificationAlert
      v-if="errorMessage"
      :message="errorMessage"
      type="error"
      :visible="true"
      @close="errorMessage = ''"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type GrantDto from '@/models/Grants/GrantDto'
import RemoteService from '@/services/RemoteService'
import NotificationAlert from '@/components/NotificationAlert.vue';


const dialog = ref(false)
const emit = defineEmits(['grant-created'])
const errorMessage = ref<string>('')

const newGrant = ref<GrantDto>({
  startDate: null,
  endDate: null,
  monthlyIncome: null
})

const startDateError = ref<string>('')
const endDateError = ref<string>('')
const monthlyIncomeError = ref<string>('')
const vacancyError = ref<string>('')

const startDate = ref<Date | null>(null)
const endDate = ref<Date | null>(null)

const validateStartDate = () => {
  if (!startDate.value) {
    startDateError.value = 'Data de início é obrigatória'
  } else {
    startDateError.value = ''
  }
}

const validateEndDate = () => {
  if (!endDate.value) {
    endDateError.value = 'Data de fim é obrigatória'
  } else {
    endDateError.value = ''
  }
}

const validateMonthlyIncome = () => {
  if (!newGrant.value.monthlyIncome) {
    monthlyIncomeError.value = 'O valor mensal é obrigatório'
  } else if (newGrant.value.monthlyIncome <= 0) {
    monthlyIncomeError.value = 'Valor mensal inválido'
  } else {
    monthlyIncomeError.value = ''
  }
}

const validateVacancy = () => {
  if (!newGrant.value.vacancy) {
    vacancyError.value = 'Vagas são obrigatórias'
  } else if (newGrant.value.vacancy <= 0) {
    vacancyError.value = 'Número de vagas inválido'
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
    await saveGrant()
    
  }
}

const saveGrant = async () => {
  try {
    newGrant.value.startDate = convertToUTC(startDate.value?.toISOString())
    newGrant.value.endDate = convertToUTC(endDate.value?.toISOString())
    await RemoteService.createGrant(newGrant.value)
  } catch (error) {
    errorMessage.value = error.response.data.message
    return
  }

  newGrant.value = {
    startDate: null,
    endDate: null,
    monthlyIncome: null
  }
  dialog.value = false
  emit('grant-created')
}
</script>
