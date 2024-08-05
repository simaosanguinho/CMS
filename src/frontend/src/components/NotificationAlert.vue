<template>
  <v-dialog v-model="dialogVisible" max-width="500" @click:outside="handleOutsideClick">
    <v-card class="notification-card">
      <v-card-title>
        <span v-if="type === 'success'">Sucesso</span>
        <span v-else-if="type === 'info'">Informação</span>
        <span v-else-if="type === 'warning'">Aviso</span>
        <span v-else-if="type === 'error'">Erro</span>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text>
        <v-alert v-if="message" :type="type" dismissible>{{ message }}</v-alert>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="plain" @click="closeNotification">Fechar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'

const props = defineProps<{
  message: string | null
  type: 'success' | 'info' | 'warning' | 'error'
  visible: boolean
}>()

const emit = defineEmits(['close'])

const dialogVisible = ref(props.visible)

watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

const closeNotification = () => {
  dialogVisible.value = false
  emit('close')
}

const handleOutsideClick = () => {
  closeNotification()
}
</script>

<style scoped>
.notification-card {
  padding: 16px;
  overflow: hidden;
}

.close-icon {
  cursor: pointer;
  margin-right: 16px;
}

.title {
  font-weight: bold;
  flex-grow: 1;
}
</style>
