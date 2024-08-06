<template>
    <v-dialog v-model="dialogVisible" max-width="500" @click:outside="handleOutsideClick">
      <v-card>
        <v-card-title class="d-flex justify-center align-center">
          <span class="title">{{ titleText }}</span>
          <v-icon class="close-icon ml-2">mdi-trophy</v-icon>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                v-for="grantee in grantees"
                :key="grantee.id"
                cols="12"
                class="d-flex align-center"
              >
                <v-icon left>mdi-account</v-icon>
                <span class="flex-grow-1 mx-2 text-h6 font-weight-black">{{ grantee.name }}
                  <v-chip small class="font-weight-black">
                    IST ID: {{ grantee.istID }}
                  </v-chip>
                </span>
                <v-icon
                  class="email-icon ml-5"
                  @click="sendEmail(grantee.email ?? '')"
                  label="Enviar email"
                >mdi-email</v-icon>
              </v-col>
            </v-row>
          </v-container>
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
  import type CandidateDto from '@/models/candidates/CandidateDto';
  import { ref, watch, defineProps, defineEmits, onMounted } from 'vue';
  import confetti from 'canvas-confetti';
  
  const props = defineProps<{
    grantees: CandidateDto[];
    visible: boolean;
  }>();
  
  const titleText = ref('Vencedores de Bolsas');
  
  const emit = defineEmits(['close']);
  
  const dialogVisible = ref(props.visible);
  
  watch(() => props.visible, (newVal) => {
    dialogVisible.value = newVal;
    if (newVal) {
      showConfetti();
    }
    getTitleText();
  });
  
  const closeNotification = () => {
    dialogVisible.value = false;
    emit('close');
  };
  
  const handleOutsideClick = () => {
    closeNotification();
  };
  
  const getTitleText = () => {
    if (props.grantees.length === 1) {
      titleText.value = 'Vencedor da Bolsa';
    } else {
      titleText.value = 'Vencedores da Bolsas';
    }
  };
  
  onMounted(() => {
    getTitleText();
    if (props.visible) {
      showConfetti();
    }
  });
  
  const sendEmail = (email: string) => {
    window.location.href = `mailto:${email}`;
  };
  
  const showConfetti = () => {
    confetti({
      particleCount: 100,
      spread: 70,
      origin: { y: 0.6 }
    });
  };
  </script>
  
  <style scoped>
  .notification-card {
    padding: 16px;
    overflow: hidden;
  }
  </style>
  