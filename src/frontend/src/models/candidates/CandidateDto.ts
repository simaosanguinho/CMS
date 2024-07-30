export default class CandidateDto {
    id?: string
    name?: string
    email?: string
    istID?: boolean
  
    constructor(jsonObj: Partial<CandidateDto>) {
      Object.assign(this, jsonObj)
    }
}
  