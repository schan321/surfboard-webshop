import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private langKey = 'language';

  constructor(private translate: TranslateService) {
    const saved = localStorage.getItem(this.langKey) || 'nl';
    this.translate.use(saved);
  }

  getCurrentLang(): string {
    return this.translate.currentLang;
  }

  switchLanguage(lang: string): void {
    this.translate.use(lang);
    localStorage.setItem(this.langKey, lang);
  }
}
