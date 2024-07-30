import axios from '../plugin/axios.ts';
import {UsersLoginRequest} from "../types/users.ts";
import {API_LOGIN} from "../api/apiPoints.ts";

export const login = async (loginBody: UsersLoginRequest) => {
  try {
    const usersStatusResponse = await axios.post(API_LOGIN, loginBody);
    return usersStatusResponse.data;
  } catch (e) {
    console.error('Error during login:', e);
    throw e;
  }
}