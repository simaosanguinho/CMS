<template>
    <v-dialog v-model="dialogVisible" max-width="900" @click:outside="handleOutsideClick">
      <v-card>
        <v-card-title>Avaliar Candidato</v-card-title>
        <v-card-text>
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
  import type EnrollmentDto from '@/models/enrollments/EnrollmentDto'
  
  const props = defineProps<{enrollment: EnrollmentDto; visible: boolean }>()
  const emit = defineEmits(['close', 'candidate-evaluated'])
  const scores = ref<number[]>([])
  
  const dialogVisible = ref(false)


  
  watch(
    () => props.visible,
    (newVal) => {
      dialogVisible.value = newVal
      if (props.enrollment.id) {
        getEvaluation()
      }
    }
  )
  
  const handleSave = async () => {
    await saveEvaluation()
    closeDialog()
  }
  
  const saveEvaluation = async () => {
    try {
    if (props.enrollment.id) {
        await RemoteService.evaluateCandidate(props.enrollment.id, scores.value)
    }
      emit('candidate-evaluated')
    } catch (error) {
      console.log("ERROR:", error)
    }
  }
  
  const closeDialog = () => {
    console.log('props.enrollment:', props.enrollment.id)
    dialogVisible.value = false
    emit('close')
  }

  const getEvaluation = async () => {
    if (props.enrollment.id) {
    const evaluation = RemoteService.getEvaluationByEnrollmentId(props.enrollment.id ?? '').then(
        (data) => {
          console.log('Evaluation:', data)
        scores.value = data.scores ?? []
        }
      )
      console.log('Evaluation:', evaluation)
    }
  }
  
  const handleOutsideClick = () => {
    closeDialog()
  }
  </script>
  