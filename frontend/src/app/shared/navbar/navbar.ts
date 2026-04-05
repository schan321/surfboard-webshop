import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { CartService } from '../../core/services/cart.service';
import { LanguageService } from '../../core/services/language.service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, TranslateModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar implements OnInit {
  protected isLoggedIn = false;
  protected isAdmin = false;
  protected cartCount = 0;
  protected menuOpen = false;
  protected currentLang: string;

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router,
    private languageService: LanguageService,
  ) {
    this.currentLang = this.languageService.getCurrentLang();
  }

  ngOnInit(): void {
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      this.isLoggedIn = this.authService.isLoggedIn();
      this.isAdmin = this.authService.isAdmin();
      this.menuOpen = false;
    });

    this.isLoggedIn = this.authService.isLoggedIn();
    this.isAdmin = this.authService.isAdmin();

    this.cartService.getItems().subscribe((items) => {
      this.cartCount = items.reduce((count, item) => count + item.quantity, 0);
    });
  }

  protected toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  protected closeMenu(): void {
    this.menuOpen = false;
  }

  protected switchLanguage(lang: string): void {
    this.languageService.switchLanguage(lang);
    this.currentLang = lang;
  }

  protected logout(): void {
    this.authService.logout();
    this.isLoggedIn = false;
    this.isAdmin = false;
    this.menuOpen = false;
    this.router.navigate(['/']);
  }
}
