import axios from 'axios'

import MaterialDto from '@/models/materials/MaterialDto'
import CandidateDto from '@/models/candidates/CandidateDto'
import GrantDto from '@/models/grants/GrantDto'

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class RemoteService {

  // ------------------- Materials -------------------
  static async getMaterials(): Promise<MaterialDto[]> {
    return httpClient.get('/materials/all').then((response) => {
      return response.data.map((material: any) => {
        return new MaterialDto(material)
      })
    })
  }

  static async createMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.post('/materials/create', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async updateMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.put('/materials/update', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async deleteMaterial(material: MaterialDto): Promise<void> {
    return httpClient.delete(`/materials/delete/${material.id}`)
  }

  // ------------------- Candidates -------------------
  static async getCandidates(): Promise<CandidateDto[]> {
    return httpClient.get('/candidates/all').then((response) => {
      console.log("GETTING CANDIDATES")
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate)
      })
    })
  }

  static async createCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.post('/candidates/create', candidate).then((response) => {
      return new CandidateDto(response.data)
    }).catch((error) => {
      console.log("ERROR CREATING CANDIDATE: ", error)
      throw error
    })
  }

  static async updateCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.put('/candidates/update', candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  static async deleteCandidate(candidate: CandidateDto): Promise<void> {
    return httpClient.delete(`/candidates/delete/${candidate.id}`)
  }
  

  // ------------------- Grants -------------------

  static async createGrant(grant: GrantDto): Promise<GrantDto> {
    return httpClient.post('/grants/create', grant).then((response) => {
      return new GrantDto(response.data)
    })
  }

  static async getGrants(): Promise<GrantDto[]> {
    return httpClient.get('/grants/all').then((response) => {
      return response.data.map((grant: any) => {
        return new GrantDto(grant)
      })
    })
  }

  static async updateGrant(grant: GrantDto): Promise<GrantDto> {
    return httpClient.put('/grants/update', grant).then((response) => {
      return new GrantDto(response.data)
    })
  }

  static async deleteGrant(grant: GrantDto): Promise<void> {
    return httpClient.delete(`/grants/delete/${grant.id}`)
  }

  static async getGrantById(id: number): Promise<GrantDto> {
    console.log("GETTING GRANT BY ID: ", id)
    return httpClient.get(`/grants/${id}`).then((response) => {
      return new GrantDto(response.data)
    })
  }

  // ------------------- Enrollments -------------------
  static async enrollCandidate(candidateId: string, grantId: string): Promise<void> {
    return httpClient.post(`/grants/${grantId}/enrollments/${candidateId}`, { candidateId, grantId })
  }

  static getEnrollmentsByGrantId(grantId: string): Promise<CandidateDto[]> {
    return httpClient.get(`/grants/${grantId}/enrollments`).then((response) => {
      return response.data.map((enrollment: any) => {
        return new CandidateDto(enrollment)
      })

    })
  }

  static getEnrollmentsByCandidateId(candidateId: string): Promise<GrantDto[]> {
    return httpClient.get(`/candidates/${candidateId}/enrollments`).then((response) => {
      return response.data.map((grant: any) => {
        return new GrantDto(grant)
      })
    })
  }

  static async unenrollCandidate(enrollmentId: string): Promise<void> {
    return httpClient.delete(`/grants/enrollments/${enrollmentId}`)
  }

  static async getUnenrolledCandidates(grantId: string): Promise<CandidateDto[]> {
    return httpClient.get(`/grants/${grantId}/unenrolled`).then((response) => {
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate)
      })
    })
  }

  static async getEnrolledGrantsByCandidateId(candidateId: string): Promise<GrantDto[]> {
    console.log("GETTING ENROLLED GRANTS BY CANDIDATE ID: ", candidateId)
    return httpClient.get(`/grants/${candidateId}/enrolled`).then((response) => {
      return response.data.map((grant: any) => {
        console.log("GRANT: ", grant)
        return new GrantDto(grant)
      })
    })
  }
}
