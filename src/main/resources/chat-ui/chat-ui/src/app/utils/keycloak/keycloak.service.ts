import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined

  constructor() { }

  get Keycloak(){
    if(!this._keycloak){
      this._keycloak =  new Keycloak({
        url: 'http://localhost:9090',
        realm: 'chat-app-realm',
        clientId: 'chat-app-client'
      });
    }
    return this._keycloak;
  }

  async init() {
    const authenticated = await this.Keycloak.init({
      onLoad: 'login-required'
    });
  }

async login(){
  await this.Keycloak.login();
}

get userId(): string {
  return this.Keycloak?.tokenParsed?.sub as string
}

  get isTokenValid():boolean{
    return !this.Keycloak.isTokenExpired();
  }

  get fullName():string{
   return this.Keycloak.tokenParsed?.['name'] as string;
  }

  logout(){
    return this.Keycloak.logout({redirectUri: 'http://localhost:4200'})
  }

  accountManagement(){
    return this.Keycloak.accountManagement();
  }

}
