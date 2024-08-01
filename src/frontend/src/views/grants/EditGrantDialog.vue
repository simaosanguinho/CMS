<template>
  <v-dialog v-model="dialogVisible" max-width="900">
    <v-card>
      <v-card-title>Edit Grant</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6">
            <v-date-picker
              v-model="localStartDate"
              label="Start Date*"
              required
              class="mt-3"
            ></v-date-picker>
          </v-col>
          <v-col cols="6">
            <v-date-picker
              v-model="localEndDate"
              label="End Date*"
              required
              class="mt-3"
            ></v-date-picker>
          </v-col>
        </v-row>

        <v-text-field
          label="Monthly Income*"
          required
          v-model="localGrant.monthlyIncome"
          type="number"
          :error-messages="monthlyIncomeError"
          @input="validateMonthlyIncome"
        ></v-text-field>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="plain" @click="closeDialog">Close</v-btn>
        <v-btn color="primary" variant="tonal" @click="handleSave">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import { ISOtoString } from '@/services/ConvertDateService'

const props = defineProps<{ grant: GrantDto | null; visible: boolean }>()
const emit = defineEmits(['close', 'grant-updated'])

const dialogVisible = ref(false)
const localGrant = ref<GrantDto>({ id: '', startDate: null, endDate: null, monthlyIncome: 0 })
const localStartDate = ref<Date | null>(null)
const localEndDate = ref<Date | null>(null)
const monthlyIncomeError = ref<string>('')

watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
  if (props.grant) {
    console.log('Type of props.grant.startDate:', typeof props.grant.startDate)
    localGrant.value = { ...props.grant }
    localStartDate.value = props.grant.startDate ? new Date(props.grant.startDate) : null
    localEndDate.value = props.grant.endDate ? new Date(props.grant.endDate) : null
  }
})

watch(() => dialogVisible.value, (newVal) => {
  if (!newVal) {
    emit('close')
  }
})

const validateMonthlyIncome = () => {
  if (!localGrant.value.monthlyIncome) {
    monthlyIncomeError.value = 'Monthly income is required'
  } else {
    monthlyIncomeError.value = ''
  }
}

const handleSave = async () => {
  validateMonthlyIncome()
  if (!monthlyIncomeError.value && localGrant.value) {
    localGrant.value.startDate = localStartDate.value ? ISOtoString(localStartDate.value) : null
    localGrant.value.endDate = localEndDate.value ? ISOtoString(localEndDate.value) : null
    await saveGrant()
    closeDialog()
  }
}

const convertToUTC = (date: string | null): string | null => {
  if (date) {
    const dateObj = new Date(date);
    return new Date(Date.UTC(
      dateObj.getFullYear(),
      dateObj.getMonth(),
      dateObj.getDate(),
      dateObj.getHours(),
      dateObj.getMinutes()
    )).toISOString();
  }
  return null;
}

const saveGrant = async () => {
  try {
    localGrant.value.startDate = convertToUTC(localGrant.value.startDate);
    localGrant.value.endDate = convertToUTC(localGrant.value.endDate);
    await RemoteService.updateGrant(localGrant.value)
    emit('grant-updated')
  } catch (error) {
    console.log(error.response.data.message)
  }
}

const closeDialog = () => {
  dialogVisible.value = false
}
</script>
