import { InjectionToken } from '@angular/core';

export interface SheetConfig {
}

export const DefaultSheetConfig : SheetConfig = {
}

export const SheetConfigToken = new InjectionToken<SheetConfig>("SheetConfig");
