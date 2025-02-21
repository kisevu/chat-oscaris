import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from '../keycloak/keycloak.service';

export const keycloakHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeycloakService);
  const token = keycloakService.Keycloak.token;
  if(token){
    const authReq = req.clone({
      headers: new HttpHeaders({
        Authorization:`Bearer ${token}`
      })
    });
    // added the header and below we're moving to next filter chain
    // here the request done changed due to the headers update
    return next(authReq);
  }

  // here we move to the next filter without the request changing
  return next(req);
};
