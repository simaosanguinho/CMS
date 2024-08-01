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

      <v-card prepend-icon="mdi-account" title="Nova Bolsa">
        <v-card-text>
          <v-row>
            <v-col cols="6">
              <v-text-field
                label="Start Date"
                :value="formattedStartDate"
                disabled
              ></v-text-field>
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
              <v-text-field
                label="End Date"
                :value="formattedEndDate"
                disabled
              ></v-text-field>
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

          <v-text-field
            label="Monthly Income (€)*"
            required
            v-model="newGrant.monthlyIncome"
            type="number"
            :error-messages="monthlyIncomeError"
            @input="validateMonthlyIncome"
          ></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="handleSave"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type GrantDto from '@/models/Grants/GrantDto'
import RemoteService from '@/services/RemoteService'
import { stringToISO } from '@/services/ConvertDateService'

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
  if (!newGrant.value.monthlyIncome || newGrant.value.monthlyIncome < 0) {
    monthlyIncomeError.value = 'Monthly Income is required'
  } else {
    monthlyIncomeError.value = ''
  }
}

const convertToUTC = (date: string | null): string | null => {
  if (date) {
    const dateObj = new Date(date);
    return new Date(Date.UTC(
      dateObj.getFullYear(),
      dateObj.getMonth(),
      dateObj.getDate(),
    )).toISOString();
  }
  return null;
}

const handleSave = async () => {
  validateStartDate()
  validateEndDate()
  validateMonthlyIncome()
  if (!startDateError.value && !endDateError.value && !monthlyIncomeError.value) {
    console.log('newGrant.value:', newGrant.value)
    await saveGrant()
    dialog.value = false
  }
}

const saveGrant = async () => {
  try {
    newGrant.value.startDate = convertToUTC(newGrant.value.startDate);
    newGrant.value.endDate = convertToUTC(newGrant.value.endDate);
    await RemoteService.createGrant(newGrant.value)
  } catch (error) {
    console.log((error as any).response.data.message)
  }

  newGrant.value = {
    startDate: null,
    endDate: null,
    monthlyIncome: null,
  }
  emit('grant-created')
}

const formattedStartDate = computed(() => {
  if (newGrant.value.startDate) {
    const date = new Date(newGrant.value.startDate);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
  }
  return '';
});

const formattedEndDate = computed(() => {
  if (newGrant.value.endDate) {
    const date = new Date(newGrant.value.endDate);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
  }
  return '';
});

</script>
