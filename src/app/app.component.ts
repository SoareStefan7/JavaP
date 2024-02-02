import { Component } from '@angular/core';
import { AuthService, RedirectLoginOptions } from '@auth0/auth0-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private auth: AuthService) {}

 register() {
  const options: RedirectLoginOptions<unknown> = {
    screen_hint: 'signup',  // This is a custom hint for Auth0 to show the signup screen
  };

  // Initiate Auth0 login with redirect
  this.auth.loginWithRedirect(options);
  console.log('Registration button clicked!');
}

}
