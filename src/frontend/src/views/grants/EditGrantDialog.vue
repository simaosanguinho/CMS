<template>
  <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
    <v-card>
      <v-card-title>Edit Grant</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6">
            <v-card-title class="text-center justify-center">
              <h4>Data de Início</h4>
            </v-card-title>
            <v-divider></v-divider>
            <v-date-picker
              v-model="localStartDate"
              label="Data de Início*"
              required
              class="mt-3"
            ></v-date-picker>
          </v-col>
          <v-col cols="6">
            <v-card-title class="text-center justify-center">
              <h4>Data de Fim</h4>
            </v-card-title>
            <v-divider></v-divider>
            <v-date-picker
              v-model="localEndDate"
              label="Data de Fim*"
              required
              class="mt-3"
            ></v-date-picker>
          </v-col>
        </v-row>
        <v-row>
          <v-text-field
            label="Valor Mensal (€)*"
            required
            v-model="localGrant.monthlyIncome"
            type="number"
            :error-messages="monthlyIncomeError"
            @input="validateMonthlyIncome"
            class="mx-6"
          ></v-text-field>

          <v-text-field
            label="Vagas*"
            required
            v-model="localGrant.vacancy"
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
        <v-btn variant="plain" @click="closeDialog">Close</v-btn>
        <v-btn color="primary" variant="tonal" @click="handleSave">Save</v-btn>
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
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import { ISOtoString } from '@/services/ConvertDateService'
import NotificationAlert from '@/components/NotificationAlert.vue'

const props = defineProps<{ grant: GrantDto | null; visible: boolean }>()
const emit = defineEmits(['close', 'grant-updated'])

const dialogVisible = ref(false)
const localGrant = ref<GrantDto>({ id: '', startDate: null, endDate: null, monthlyIncome: 0 })
const localStartDate = ref<Date | null>(null)
const localEndDate = ref<Date | null>(null)
const monthlyIncomeError = ref<string>('')
const startDateError = ref<string>('')
const endDateError = ref<string>('')
const vacancyError = ref<string>('')
const errorMessage = ref<string>('')

watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal
    if (props.grant) {
      localGrant.value = { ...props.grant }
      localStartDate.value = props.grant.startDate ? new Date(props.grant.startDate) : null
      localEndDate.value = props.grant.endDate ? new Date(props.grant.endDate) : null
    }
  }
)

watch(
  () => dialogVisible.value,
  (newVal) => {
    if (!newVal) {
      emit('close')
    }
  }
)

const validateMonthlyIncome = () => {
  if (!localGrant.value.monthlyIncome) {
    monthlyIncomeError.value = 'Valor mensal é obrigatório'
  } else {
    monthlyIncomeError.value = ''
  }
}

const validateVacancy = () => {
  if (!localGrant.value.vacancy) {
    vacancyError.value = 'Vaga é obrigatória'
  } else if (localGrant.value.vacancy <= 0) {
    vacancyError.value = 'Número de vagas inválido'
  } else {
    vacancyError.value = ''
  }
}

const validateStartDate = () => {
  if (!localGrant.value.startDate) {
    startDateError.value = 'Data de Início é obrigatória'
  } else {
    startDateError.value = ''
  }
}

const validateEndDate = () => {
  if (!localGrant.value.endDate) {
    endDateError.value = 'Data de Fim é obrigatória'
  } else {
    endDateError.value = ''
  }
}

const handleSave = async () => {
  validateMonthlyIncome()
  validateVacancy()
  validateEndDate()
  validateStartDate()
  if (
    !monthlyIncomeError.value &&
    !vacancyError.value &&
    !startDateError.value &&
    !endDateError.value
  ) {
    localGrant.value.startDate = localStartDate.value ? ISOtoString(localStartDate.value) : null
    localGrant.value.endDate = localEndDate.value ? ISOtoString(localEndDate.value) : null
    await saveGrant()
    
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

const saveGrant = async () => {
  try {
    localGrant.value.startDate = convertToUTC(localGrant.value.startDate)
    localGrant.value.endDate = convertToUTC(localGrant.value.endDate)
    console.log('Local grant after conversion:', localGrant.value)
    await RemoteService.updateGrant(localGrant.value)
    
  } catch (error) {
    errorMessage.value = error.response.data.message
    return
  }
  closeDialog()
  emit('grant-updated')
}

const closeDialog = () => {
  dialogVisible.value = false
}

const handleOutsideClick = () => {
  closeDialog()
}
</script>
