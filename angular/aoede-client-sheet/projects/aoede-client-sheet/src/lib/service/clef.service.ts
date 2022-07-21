import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Clef } from '../model/clef.model';
import { MappedClef, staveExtentionInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class ClefService implements SingleCanvasService<Clef, MappedClef> {

	constructor() { }

	public map  (source : Clef, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedClef {
		console.log("mapping clef " + JSON.stringify(source));
		return {
			...staveExtentionInitializer,
			clef   : source,
			width  : staveConfig.noteSpacing *  6,
			header : staveConfig.noteSpacing * 10 + staveConfig.noteSpacing * source.spos,
			footer : staveConfig.noteSpacing *  6 - staveConfig.noteSpacing * source.spos
		};
	}

	public draw (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		//let previousStype = context.fillStyle;
		//context.fillStyle = 'blue';
		//context.fillRect(x, y - clef.header, clef.width, clef.header);
		//context.fillStyle = 'red';
		//context.fillRect(x, y              , clef.width, clef.footer);
		switch (clef.clef.type) {
			case "G": this.drawClefG(clef, staveConfig, context, x, y - clef.header); break;
			case "C": this.drawClefC(clef, staveConfig, context, x, y - clef.header); break;
			case "F": this.drawClefF(clef, staveConfig, context, x, y - clef.header); break;
			default : this.drawClefX(clef, staveConfig, context, x, y - clef.header); break;
		}
		//context.fillStyle = previousStype;
	}

	private drawClefG (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		context.save();
		context.strokeStyle="rgba(0,0,0,0)";
		context.miterLimit=4;
		context.font="15px ''";
		context.font="   15px ''";
		context.translate(x, y);
		context.scale(0.9877518767285658 * staveConfig.scale * 2, 0.9877518767285658 * staveConfig.scale * 2);
		context.save();
		context.font="   15px ''";
		context.beginPath();
		context.moveTo(12.049,3.5296);
		context.bezierCurveTo(12.354,6.6559,10.03,9.1859,7.971799999999999,11.231);
		context.bezierCurveTo(7.036899999999999,12.128,7.816799999999999,11.379,7.328099999999999,11.825);
		context.bezierCurveTo(7.225899999999999,11.346,7.029499999999999,10.094,7.047899999999999,9.715);
		context.bezierCurveTo(7.178299999999999,7.0211,9.3677,3.1274999999999995,11.286,1.6913999999999998);
		context.bezierCurveTo(11.594999999999999,2.2680999999999996,11.849,2.3145,12.049,3.5296);
		context.closePath();
		context.moveTo(12.7,19.671599999999998);
		context.bezierCurveTo(11.468,18.7656,9.85,18.5276,8.366399999999999,18.786599999999996);
		context.bezierCurveTo(8.175099999999999,17.531599999999997,7.983699999999999,16.276599999999995,7.792399999999999,15.022599999999997);
		context.bezierCurveTo(10.142999999999999,12.693599999999996,12.698999999999998,9.990399999999998,12.832999999999998,6.4831999999999965);
		context.bezierCurveTo(12.891999999999998,4.251199999999996,12.556999999999999,1.8117999999999963,11.154999999999998,-0.00040000000000350866);
		context.bezierCurveTo(9.454599999999997,0.1278299999999965,8.255499999999998,2.1555999999999966,7.353099999999998,3.4160999999999966);
		context.bezierCurveTo(5.864199999999998,6.086599999999997,6.211699999999998,9.332999999999997,6.7830999999999975,12.212599999999997);
		context.bezierCurveTo(5.973699999999997,13.164599999999997,4.853499999999998,13.955599999999997,4.055699999999998,14.946599999999997);
		context.bezierCurveTo(1.699599999999998,17.254599999999996,-0.352800000000002,20.376599999999996,0.05109999999999815,23.824599999999997);
		context.bezierCurveTo(0.23441999999999816,27.158599999999996,2.640499999999998,30.258599999999998,5.921299999999998,31.051599999999997);
		context.bezierCurveTo(7.166999999999998,31.3666,8.485199999999997,31.397599999999997,9.745399999999998,31.150599999999997);
		context.bezierCurveTo(9.9653,33.4006,10.771999999999998,35.779599999999995,9.837899999999998,37.9636);
		context.bezierCurveTo(9.137199999999998,39.5616,7.050399999999998,40.9676,5.505399999999998,40.1556);
		context.bezierCurveTo(4.905999999999998,39.8396,5.391699999999998,40.1046,5.027399999999998,39.9036);
		context.bezierCurveTo(6.097199999999998,39.6466,7.026999999999998,38.867599999999996,7.287399999999998,38.3386);
		context.bezierCurveTo(8.125199999999998,36.8746,6.887599999999998,34.6996,5.131999999999998,34.9806);
		context.bezierCurveTo(2.869999999999998,35.0266,1.941599999999998,38.1206,3.396399999999998,39.665600000000005);
		context.bezierCurveTo(4.743199999999998,41.18560000000001,7.229399999999998,40.9776,8.8265,39.9836);
		context.bezierCurveTo(10.639,38.8036,10.866,36.439600000000006,10.658999999999999,34.421600000000005);
		context.bezierCurveTo(10.588999999999999,33.74360000000001,10.255999999999998,31.751600000000003,10.214999999999998,31.034600000000005);
		context.bezierCurveTo(10.911999999999997,30.785600000000006,10.423999999999998,30.975600000000004,11.407999999999998,30.585600000000003);
		context.bezierCurveTo(14.067999999999998,29.532600000000002,15.764999999999997,26.326600000000003,15.001999999999997,23.463600000000003);
		context.bezierCurveTo(14.683999999999997,21.994600000000002,13.957999999999997,20.5496,12.699999999999998,19.671600000000005);
		context.closePath();
		context.moveTo(13.261,25.428599999999996);
		context.bezierCurveTo(13.475,27.419599999999996,12.207999999999998,29.749599999999994,10.181999999999999,30.388599999999997);
		context.bezierCurveTo(10.046,29.593599999999995,10.009999999999998,29.377599999999997,9.919399999999998,28.913599999999995);
		context.bezierCurveTo(9.437199999999997,26.453599999999994,9.175399999999998,23.926599999999993,8.803399999999998,21.432599999999994);
		context.bezierCurveTo(10.427999999999997,21.264599999999994,12.260999999999997,21.975599999999993,12.825999999999997,23.616599999999995);
		context.bezierCurveTo(13.069999999999997,24.193599999999996,13.168999999999997,24.813599999999994,13.260999999999997,25.428599999999996);
		context.closePath();
		context.moveTo(8.1124,30.624599999999994);
		context.bezierCurveTo(5.568299999999999,30.765599999999992,3.112899999999999,29.029599999999995,2.4780999999999995,26.543599999999994);
		context.bezierCurveTo(1.7290999999999994,24.390599999999996,1.9497999999999995,21.913599999999995,3.2987999999999995,20.039599999999993);
		context.bezierCurveTo(4.4139,18.337599999999995,5.9052999999999995,16.934599999999993,7.327399999999999,15.496599999999994);
		context.bezierCurveTo(7.510399999999999,16.623599999999993,7.693399999999999,17.750599999999995,7.876399999999999,18.878599999999995);
		context.bezierCurveTo(4.8858,19.660599999999995,2.8717999999999995,23.603599999999993,4.6613999999999995,26.329599999999996);
		context.bezierCurveTo(5.1937999999999995,27.093599999999995,6.637899999999999,28.552599999999995,7.4269,27.963599999999996);
		context.bezierCurveTo(6.3248999999999995,27.280599999999996,5.4236,26.104599999999998,5.6174,24.736599999999996);
		context.bezierCurveTo(5.5353,23.454599999999996,6.987299999999999,21.825599999999994,8.268699999999999,21.538599999999995);
		context.bezierCurveTo(8.707099999999999,24.407599999999995,9.209999999999999,27.611599999999996,9.648399999999999,30.481599999999993);
		context.bezierCurveTo(9.142999999999999,30.581599999999995,8.627299999999998,30.624599999999994,8.1124,30.624599999999994);
		context.closePath();
		context.fill();
		context.stroke();
		context.restore();
		context.restore();
	}

	private drawClefC (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let previousStype = context.fillStyle;
		context.fillStyle = 'yellow';
		this.drawClefG(clef, staveConfig, context, x, y);
		context.fillStyle = previousStype;
	}

	private drawClefF (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let previousStype = context.fillStyle;
		context.fillStyle = 'orange';
		this.drawClefG(clef, staveConfig, context, x, y);
		context.fillStyle = previousStype;
	}

	private drawClefX (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let previousStype = context.fillStyle;
		context.fillStyle = 'orange';
		this.drawClefC(clef, staveConfig, context, x, y);
		context.fillStyle = previousStype;
	}

}
