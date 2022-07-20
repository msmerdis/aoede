import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

export interface CanvasService<Source, Target, Mapped> {
	map  (source : Source, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): Target;
	draw (mapped : Mapped, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void;
};

export interface SingleCanvasService<Source, Target> extends CanvasService<Source, Target, Target> {};
export interface ArrayCanvasService<Source, Target> extends CanvasService<Source[], Target[], Target> {};