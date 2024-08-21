# CMS: Candidates Management System (DMS)

This repository contains my implementation of the **CMS: Candidates Management System**, developed as part of a recruitment process for the DEI (Department of Informatics Engineering) at IST (Instituto Superior Técnico). This project was completed to fulfill the requirements of the **BL121/2024** practical exercise.

## Project Overview

The **Candidates Management System (CMS)** is a subsistema developed to facilitate the management of candidates applying for DEI scholarships. The CMS is a module within the broader DEI Management System (DMS), a platform used internally by DEI staff.

### Key Features

The CMS provides the following functionalities:

1. **Candidates Management**:
   - View all candidates in a quick-access table displaying at least their name and IST ID.
   - Add new candidates.
   - Update and remove existing candidates.
   - View all scholarships a candidate has applied for.

2. **Scholarships Management**:
   - View all scholarships in a quick-access table displaying at least the scholarship ID, start date, and end date.
   - View detailed information on a specific scholarship, including start date, end date, monthly value, and enrolled candidates.
   - Add new scholarships.
   - Update and remove existing scholarships.
   - Enroll and unenroll candidates in existing scholarships.

3. **Evaluation Management within Scholarships**:
   - Evaluate candidates based on categories like "Curricular Assessment," "Interview," and "Practical Exercise."
   - Adjust the weight of each evaluation category within a scholarship.
   - Calculate and display the final average score for each candidate based on the assigned grades and category weights.
   - Select the winning candidate(s) once evaluations are completed.

### Technologies Used

- **Backend**: Spring Boot v2
- **Frontend**: Vue.js v3

### Additional Features

While the core features above were prioritized, I also implemented some additional features to enhance the user experience, such as a dashboard for statistics and a homepage for easy navigation.

### Implementation Notes

- The project was developed modularly, with a focus on clean, maintainable code and a user-friendly web interface.
- User input validation is implemented to ensure data integrity and prevent errors.
- Although the current version of the CMS is intended for use only within the DEI staff network, it can be expanded with authentication and authorization mechanisms in the future.

Instructions on how to run the project can be found in [here](/enunciado.md).
