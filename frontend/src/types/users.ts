export interface UserState {
  name: string;
  age: number;
  isLoggedIn: boolean;
}

export interface UserGetters {
  userInfo: (state: UserState) => {
    name: string;
    age: number;
    isLoggedIn: boolean;
  };
}

export interface UserActions {
  setName(newName: string): void;
  setAge(newAge: number): void;
  logIn(): void;
  logOut(): void;
}
