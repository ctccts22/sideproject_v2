import axios from '../plugin/axios.ts';
import {UsersLoginRequest} from "../types/users.ts";

export const login = async (loginBody: UsersLoginRequest) => {
  try {
    const usersStatusResponse = await axios.post(`/api/auth/login`, loginBody);
    return usersStatusResponse.data;
  } catch (e) {
    console.error('Error during login:', e);
    throw e;
  }
}