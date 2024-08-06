export default class CandidateDto {
    id?: string
    name?: string
    email?: string
    istID?: number
  
    constructor(jsonObj: Partial<CandidateDto>) {
      Object.assign(this, jsonObj)
    }
}
  