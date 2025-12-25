class ApiClient {
    constructor() {
        this.baseUrl = 'http://localhost:8080/api';
    }

    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const config = { ...defaultOptions, ...options };

        try {
            const response = await fetch(url, config);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }

    async get(endpoint) {
        return this.request(endpoint, { method: 'GET' });
    }

    async post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    }

    async put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data),
        });
    }

    async delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }

    async getAllElderly() {
        return this.get('/elderly');
    }

    async getElderlyById(id) {
        return this.get(`/elderly/${id}`);
    }

    async createElderly(data) {
        return this.post('/elderly', data);
    }

    async updateElderly(id, data) {
        return this.put(`/elderly/${id}`, data);
    }

    async deleteElderly(id) {
        return this.delete(`/elderly/${id}`);
    }

    async searchElderlyByName(name) {
        return this.get(`/elderly/search/name?name=${encodeURIComponent(name)}`);
    }

    async searchElderlyByPhone(phone) {
        return this.get(`/elderly/search/phone?phone=${encodeURIComponent(phone)}`);
    }

    async searchElderlyByIdCard(idCard) {
        return this.get(`/elderly/search/idcard?idCard=${encodeURIComponent(idCard)}`);
    }

    async getAllHealthAssessments() {
        return this.get('/health-assessments');
    }

    async getHealthAssessmentsByElderlyId(elderlyId) {
        return this.get(`/health-assessments/elderly/${elderlyId}`);
    }

    async createHealthAssessment(data) {
        return this.post('/health-assessments', data);
    }

    async updateHealthAssessment(id, data) {
        return this.put(`/health-assessments/${id}`, data);
    }

    async deleteHealthAssessment(id) {
        return this.delete(`/health-assessments/${id}`);
    }

    async getAllEnvironmentAssessments() {
        return this.get('/environment-assessments');
    }

    async getEnvironmentAssessmentsByElderlyId(elderlyId) {
        return this.get(`/environment-assessments/elderly/${elderlyId}`);
    }

    async createEnvironmentAssessment(data) {
        return this.post('/environment-assessments', data);
    }

    async updateEnvironmentAssessment(id, data) {
        return this.put(`/environment-assessments/${id}`, data);
    }

    async deleteEnvironmentAssessment(id) {
        return this.delete(`/environment-assessments/${id}`);
    }

    async getAllCareRecords() {
        return this.get('/care-records');
    }

    async getCareRecordsByElderlyId(elderlyId) {
        return this.get(`/care-records/elderly/${elderlyId}`);
    }

    async createCareRecord(data) {
        return this.post('/care-records', data);
    }

    async updateCareRecord(id, data) {
        return this.put(`/care-records/${id}`, data);
    }

    async deleteCareRecord(id) {
        return this.delete(`/care-records/${id}`);
    }
}

const apiClient = new ApiClient();
