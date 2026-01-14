import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests if available
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export interface CaveStock {
  id: number;
  label: string;
  years: number;
  genre: string;
  area: string;
  available_quantity: number;
  price: number;
  supplier_id: number;
}

export interface CaveUser {
  id?: number;
  lastname: string;
  firstname: string;
  total_bottles_bought?: number;
  email: string;
  password?: string;
  role?: string;
  address?: string;
  phone_number?: string;
}

export interface CaveHandler {
  id?: number;
  supplyGroupId?: number;
  requestAmount: number;
  operation: number;
  validated: boolean;
  completed: boolean;
  orderTimestamp: string;
  stockId: number;
  supplierId?: number;
  userId: number;
}

// Stock API
export const stockAPI = {
  getAllStock: () => apiClient.get<CaveStock[]>('/get_stock'),
  getStockByGenre: (genre: string) => apiClient.get<CaveStock[]>(`/get_stock/${genre}`),
  getStockById: (id: number) => apiClient.get<CaveStock>(`/get_stockById/${id}`),
  getStockByLabel: (label: string) => apiClient.get<CaveStock[]>(`/get_stockByLabel/${label}`),
  getStockByYear: (firstYear: number, secondYear: number) =>
    apiClient.get<CaveStock[]>(`/get_stockByYear/${firstYear}/${secondYear}`),
  getStockByPrice: (lowPrice: number, highPrice: number) =>
    apiClient.get<CaveStock[]>(`/get_stockByPrice/${lowPrice}/${highPrice}`),
};

// User API
export const userAPI = {
  getAllUsers: () => apiClient.get<CaveUser[]>('/get_users'),
  getUserById: (id: number) => apiClient.get<CaveUser[]>(`/get_user_by_id/${id}`),
  getUserByLastname: (lastname: string) => apiClient.get<any[]>(`/get_user_by_lastname/${lastname}`),
  createUser: (user: CaveUser) => apiClient.post<string>('/create_user', user),
  updateUser: (id: number, field: string, value: string) =>
    apiClient.get(`/updateUser/${id}/${field}/${value}`),
  deleteUser: (id: number) => apiClient.delete(`/delete_users/${id}`),
};

// Handler/Order API
export const orderAPI = {
  getAllHandlers: () => apiClient.get<CaveHandler[]>('/get_handler'),
  getHandlerById: (id: number) => apiClient.get<CaveHandler>(`/getInstanceById/${id}`),
  getHandlerByUserId: (userId: number) => apiClient.get<CaveHandler[]>(`/getInstanceByUserId/${userId}`),
  createHandler: (handler: CaveHandler) => apiClient.post<string>('/createInstance', handler),
  updateHandler: (id: number, field: string, value: string) =>
    apiClient.get(`/updateInstance/${id}/${field}/${value}`),
  deleteHandler: (id: number) => apiClient.delete(`/deleteInstance/${id}`),
};

export default apiClient;
