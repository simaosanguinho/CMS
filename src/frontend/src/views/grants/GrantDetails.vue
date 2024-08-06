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
          <h3>Detalhes da Bolsa</h3>
        </v-card-title>
        <v-divider></v-divider>
        <v-card class="elevation-0">
          <v-card-text class="pa-6">
            <v-row>
              <v-col>
                <div class="font-weight-black">ID</div>
                {{ grant.id }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Início</div>
                {{ formatDate(grant.startDate) }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Fim</div>
                {{ formatDate(grant.endDate) }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Valor Mensal (€)</div>
                {{ grant.monthlyIncome }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Vagas</div>
                {{ grant.vacancy }}
              </v-col>
              <v-col>
                <div class="font-weight-black">Concurso</div>
                {{ grant.onGoing ? 'A decorrer' : 'Fechado' }}
              </v-col>
            </v-row>
            <v-card-text>
              <v-row class="mt-2" justify="center">
                <div class="font-weight-black text-h7 mr-4">
                  Pesos de Avaliação

                  <v-btn
                  v-if="grant.onGoing"
                    icon
                    density="compact"
                    variant="plain"
                    class="md-5"
                    @click="editGrantWeights()"
                  >
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                </div>
              </v-row>
              <v-row justify="center" class="mt-4">
                <v-chip class="mx-5" color="primary"
                  >Avaliação Curricular ({{
                    (grant?.curricularEvaluationWeight ?? 0) * 100
                  }}%)</v-chip
                >
                <v-chip class="mx-5" color="primary"
                  >Exercício Prático ({{ (grant?.practicalExerciseWeight ?? 0) * 100 }}%)</v-chip
                >
                <v-chip class="mx-5" color="primary"
                  >Entrevista ({{ (grant?.interviewWeight ?? 0) * 100 }}%)</v-chip
                >
              </v-row>
            </v-card-text>
          </v-card-text>
          <v-divider></v-divider>
        </v-card>
      </v-col>
      <v-row justify="center">
        <v-col>
          <v-row align="center">
            <v-card-title class="pt-5">
              <v-card-title class="pt-0">
                <h4>Candidatos Inscritos ( {{ enrolledCandidates.length }} )</h4>
              </v-card-title>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-btn
              v-if="grant.onGoing"
              class="text-none font-weight-regular mr-6"
              prepend-icon="mdi-plus"
              text="Adicionar Candidato"
              color="primary"
              @click="enrollCandidates()"
            ></v-btn>
            <v-btn
              v-if="grant.onGoing"
              class="text-none font-weight-regular mr-6"
              prepend-icon="mdi-plus"
              :text="winnerButtonText"
              color="primary"
              @click="selectWinners()"
            ></v-btn>
          </v-row>
          <v-data-table
            v-if="grant.onGoing"
            :headers="headersCandidates"
            :items="enrolledCandidates"
            :search="search"
            :custom-filter="fuzzySearch"
            :hover="true"
            class="text-left"
          >
            <template v-slot:[`item.evaluation`]="{ item }">
              <v-btn
              v-if="grant.onGoing"  
                class="elevation-0 text-none font-weight-regular"
                :disabled="!grant.onGoing"
                @click="evaluateCandidate(item.enrollmentId, item.name, item.id)"
              >
                <v-chip v-if="item.isEvaluated" color="green" class="white--text" variant="flat">
                  {{ item.finalScore }}
                </v-chip>
                <v-chip v-else color="error" class="white--text"> Não avaliado </v-chip>
              </v-btn>
            </template>
            <template v-slot:[`item.actions`]="{ item }">
              <v-icon v-if="grant.onGoing" @click="unenrollCandidate(item)">mdi-delete</v-icon>
            </template>
          </v-data-table>
          <v-data-table
            v-else
            :headers="headersWinners"
            :items="enrolledCandidates"
            :hover="true"
            class="text-left"
          > 
          <template v-slot:[`item.evaluation`]="{ item }">
            <v-chip  color="green" class="white--text" variant="flat">
                  {{ item.finalScore }}
                </v-chip>
            </template>
            <template v-slot:[`item.result`]="{ item }">
              <v-icon v-if="isWinner(item.id)" color="green">mdi-trophy</v-icon>
              <v-icon v-else color="red">mdi-close</v-icon>
            </template>

            <template v-slot:[`item.contact`]="{ item }">
              <v-icon @click="sendEmail(item.email)">mdi-email</v-icon>
            </template>

            <template v-slot:[`item.actions`]="{ item }">
              <v-icon v-if="grant.onGoing" @click="unenrollCandidate(item)">mdi-delete</v-icon>
              <v-icon v-if="!grant.onGoing && isWinner(item.id)" class="ml-2" color="green">mdi-trophy</v-icon>
              <v-icon v-if="!grant.onGoing && !isWinner(item.id)" class="ml-2" color="red">mdi-close</v-icon>
              
            </template>
          </v-data-table>
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

  <EnrollCandidatesDialog
    :grant="grant"
    :visible="enrollDialogVisible"
    @close="enrollDialogVisible = false"
    @candidates-enrolled="fetchGrant(grant.id)"
  />

  <EvaluateCandidateDialog
    :enrollment="selectedEnrollment"
    :grant="grant"
    :visible="evaluateDialogVisible"
    @close="evaluateDialogVisible = false"
    @candidate-evaluated="fetchGrant(grant.id)"
  />

  <EditGrantWeightsDialog
    :grant="grant"
    :visible="editGrantWeightsDialogVisible"
    @close="editGrantWeightsDialogVisible = false"
    @weights-updated="fetchGrant(grant.id)"
  />

  <GrantWinnersAlert
    :grantees="grantWinners"
    type="error"
    :visible="grantWinnersAlertVisible"
    @close="closeGrantWinnersAlert()"
  />

  <NotificationAlert
    v-if="errorWinnerMessage"
    :message="errorWinnerMessage"
    type="error"
    :visible="true"
    @close="errorWinnerMessage = ''"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RemoteService from '@/services/RemoteService'
import type GrantDto from '@/models/grants/GrantDto'
import type CandidateDto from '@/models/candidates/CandidateDto'
import EditGrantDialog from '@/views/grants/EditGrantDialog.vue'
import EnrollCandidatesDialog from '@/views/enrollments/EnrollCandidatesDialog.vue'
import EvaluateCandidateDialog from '@/views/evaluations/EvaluateCandidateDialog.vue'
import EditGrantWeightsDialog from '@/views/grants/EditGrantWeightsDialog.vue'
import EnrollmentDto from '@/models/enrollments/EnrollmentDto'
import NotificationAlert from '@/components/NotificationAlert.vue'
import GrantWinnersAlert from '@/components/GrantWinnersAlert.vue'

const route = useRoute()
const router = useRouter()
const editDialogVisible = ref(false)
const enrollDialogVisible = ref(false)
const evaluateDialogVisible = ref(false)
const editGrantWeightsDialogVisible = ref(false)
const grant = ref<GrantDto | null>(null)
const loading = ref(true)
const enrolledCandidates = ref<CandidateDto | null>(null)
const selectedEnrollment = ref<EnrollmentDto | null>(null)
const errorWinnerMessage = ref('')
const grantWinnersAlertVisible = ref(false)
const grantWinners = ref<CandidateDto[]>([])

const search = ref('')
const headersCandidates = [
  { title: 'Nome', value: 'name', key: 'name' },
  { title: 'IST ID', value: 'istID', key: 'istID' },
  { title: 'Nota Final', value: 'evaluation', key: 'evaluation' },
  { title: '', value: 'actions', key: 'actions' }
]

const headersWinners = [
  { title: 'Nome', value: 'name', key: 'name' },
  { title: 'IST ID', value: 'istID', key: 'istID' },
  { title: 'Nota Final', value: 'evaluation', key: 'evaluation' },
  { title: 'Resultado', value: 'result', key: 'result' },
  { title: 'Contactar', value: 'contact', key: 'contact' }
]


const fetchGrant = async (id: string) => {
  try {
    grant.value = await RemoteService.getGrantById(id)
    const fetchedCandidates = await RemoteService.getEnrollmentsByGrantId(id)

    enrolledCandidates.value = fetchedCandidates.map((enrollment: any) => enrollment.candidate)

    enrolledCandidates.value.forEach((candidate: CandidateDto) => {
      const enrollment = fetchedCandidates.find(
        (enrollment: any) => enrollment.candidate.id === candidate.id
      )
      if (enrollment) {
        candidate.enrollmentId = enrollment.id
        candidate.isEvaluated = enrollment.evaluated
        candidate.finalScore = Math.round(enrollment.finalScore * 100) / 100
      }
    })
    if(!grant.value.onGoing) {
      grantWinners.value = await RemoteService.getGrantWinners(grant.value.id)

    }
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.back()
}

function editGrant(newGrant: GrantDto) {
  grant.value = newGrant
  editDialogVisible.value = true
}

const editGrantWeights = () => {
  editGrantWeightsDialogVisible.value = true
}

const deleteGrant = async () => {
  if (grant.value) {
    try {
      await RemoteService.deleteGrant(grant.value)
      router.push({ name: 'grants' })
    } catch (error) {
      console.error(error)
    }
  }
}

const enrollCandidates = () => {
  enrollDialogVisible.value = true
}

const evaluateCandidate = (enrollmentId: number, candidateName: string, candidateIstId: string) => {
  selectedEnrollment.value = {
    id: enrollmentId.toString(),
    candidateName: candidateName,
    candidateIstId: candidateIstId
  }
  evaluateDialogVisible.value = true
}
const unenrollCandidate = async (candidate: CandidateDto) => {
  if (grant.value) {
    try {
      await RemoteService.unenrollCandidate(candidate.enrollmentId)
      fetchGrant(grant.value.id)
    } catch (error) {
      console.error(error)
    }
  }
}

const selectWinners = async () => {  if (enrolledCandidates.value) {
    const unevaluatedCandidates = enrolledCandidates.value.filter(
      (candidate: CandidateDto) => !candidate.isEvaluated
    )
    if (unevaluatedCandidates.length > 0) {
      errorWinnerMessage.value = 'Existem candidatos por avaliar'
    } else if (enrolledCandidates.value.length == 0) {
      errorWinnerMessage.value = 'Não existem candidatos inscritos'
    } else {
      showGrantees(await RemoteService.getGrantWinners(grant.value.id))
    }
  }
}

const sendEmail = (email: string) => {
    window.location.href = `mailto:${email}`;
  };

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

const showGrantees = async (grantees: CandidateDto[]) => {
  if (grantees.length > 0) {
    grantWinners.value = grantees
    grantWinnersAlertVisible.value = true
  }
}

const closeGrantWinnersAlert = () => {
  grant.value.onGoing = false
  grantWinnersAlertVisible.value = false
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

// Computed property to check if a candidate is a winner
const isWinner = (candidateId: string) => {
  return grantWinners.value.some(winner => winner.id === candidateId)
}

onMounted(() => {
  const grantId = route.params.id as string
  fetchGrant(grantId)
})
</script>
