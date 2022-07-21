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
		switch (source.type) {
			case "G":
				return {
					...staveExtentionInitializer,
					clef   : source,
					width  : staveConfig.noteSpacing *  6,
					header : staveConfig.noteSpacing * 10 + staveConfig.noteSpacing * source.spos,
					footer : staveConfig.noteSpacing *  6 - staveConfig.noteSpacing * source.spos
				};
			case "C":
			case "F":
				return {
					...staveExtentionInitializer,
					clef   : source,
					width  : staveConfig.noteSpacing * 6,
					header : staveConfig.noteSpacing * 2 + staveConfig.noteSpacing * source.spos,
					footer : staveConfig.noteSpacing * 5 - staveConfig.noteSpacing * source.spos
				};
			default :
				return {
					...staveExtentionInitializer,
					clef   : source,
					width  : staveConfig.noteSpacing *  6,
					header : staveConfig.noteSpacing * 10 + staveConfig.noteSpacing * source.spos,
					footer : staveConfig.noteSpacing *  6 - staveConfig.noteSpacing * source.spos
				};
		}
	}

	public draw (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let previousStype = context.fillStyle;

		context.fillStyle = 'blue';
		context.fillRect(x, y - clef.header, clef.width, clef.header);
		context.fillStyle = 'red';
		context.fillRect(x, y              , clef.width, clef.footer);
		context.fillStyle = previousStype;

		switch (clef.clef.type) {
			case "G": this.drawClefG(clef, staveConfig, context, x, y - clef.header); break;
			case "C": this.drawClefC(clef, staveConfig, context, x, y - clef.header); break;
			case "F": this.drawClefF(clef, staveConfig, context, x, y - clef.header); break;
			default : this.drawClefX(clef, staveConfig, context, x, y - clef.header); break;
		}
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
		context.save();
		context.strokeStyle="rgba(0,0,0,0)";
		context.miterLimit=4;
		context.font="15px ''";
		context.font="   15px ''";
		context.save();
		context.font="   15px ''";
		context.translate(x, y);
		context.scale(staveConfig.scale * 1.7, staveConfig.scale * 1.7);
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.translate(-230.9546,-533.6597);
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.beginPath();
		context.moveTo(248.25999,536.802);
		context.bezierCurveTo(248.26766,537.17138,248.11044,537.54065,247.82878,537.78185);
		context.bezierCurveTo(247.46853,538.11076,246.91933,538.17813,246.47048,538.01071);
		context.bezierCurveTo(246.02563,537.83894,245.69678,537.39883,245.67145,536.9206);
		context.bezierCurveTo(245.63767,536.54689,245.75685,536.15479,246.02747,535.88867);
		context.bezierCurveTo(246.28257,535.6168,246.66244,535.48397,247.03147,535.50645);
		context.bezierCurveTo(247.41131,535.51452,247.77805,535.70601,248.00489,536.01019);
		context.bezierCurveTo(248.17962,536.23452,248.26238,536.51954,248.25999,536.802);
		context.closePath();
		context.fill();
		context.stroke();
		context.restore();
		context.restore();
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.translate(-230.9546,-533.6597);
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.beginPath();
		context.moveTo(248.25999,542.64502);
		context.bezierCurveTo(248.26772,543.01469,248.11076,543.38446,247.82878,543.62585);
		context.bezierCurveTo(247.46853,543.95476,246.91933,544.02213,246.47048,543.85472);
		context.bezierCurveTo(246.02537,543.68288,245.69655,543.24237,245.67145,542.76389);
		context.bezierCurveTo(245.63651,542.3899,245.76354,542.00308,246.027,541.733);
		context.bezierCurveTo(246.27663,541.45454,246.6606,541.3279,247.02845,541.3495);
		context.bezierCurveTo(247.5123,541.36282,247.95159,541.69251,248.15162,542.12465);
		context.bezierCurveTo(248.22565,542.2874,248.26043,542.46657,248.25999,542.64502);
		context.closePath();
		context.fill();
		context.stroke();
		context.restore();
		context.restore();
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.translate(-230.9546,-533.6597);
		context.save();
		context.fillStyle="rgba(0, 0, 0, 1)";
		context.font="   15px ''";
		context.beginPath();
		context.moveTo(243.979,540.86798);
		context.bezierCurveTo(244.02398,543.69258,242.7636,546.43815,240.76469,548.40449);
		context.bezierCurveTo(238.27527,550.89277,235.01791,552.47534,231.69762,553.53261);
		context.bezierCurveTo(231.2559,553.77182,230.5897,553.45643,231.2855,553.13144);
		context.bezierCurveTo(232.62346,552.52289,234.01319,552.0005,235.24564,551.1808);
		context.bezierCurveTo(237.96799,549.4975,240.26523,546.84674,240.82279,543.61854);
		context.bezierCurveTo(241.14771,541.65352,241.05724,539.60795,240.56484,537.67852);
		context.bezierCurveTo(240.20352,536.25993,239.22033,534.7955,237.66352,534.58587);
		context.bezierCurveTo(236.25068,534.36961,234.74885,534.85905,233.74057,535.88093);
		context.bezierCurveTo(233.47541,536.14967,232.95916,536.89403,233.04435,537.74747);
		context.bezierCurveTo(233.64637,537.27468,233.60528,537.32732,234.099,537.10717);
		context.bezierCurveTo(235.23573,536.60031,236.74349,537.32105,237.027,538.57272);
		context.bezierCurveTo(237.32909,539.72295,237.09551,541.18638,235.96036,541.7996);
		context.bezierCurveTo(234.77512,542.44413,233.02612,542.17738,232.3645,540.90866);
		context.bezierCurveTo(231.26916,538.95418,231.87147,536.28193,233.64202,534.92571);
		context.bezierCurveTo(235.44514,533.42924,238.07609,533.37089,240.19963,534.13862);
		context.bezierCurveTo(242.38419,534.95111,243.68629,537.21483,243.89691,539.45694);
		context.bezierCurveTo(243.95419,539.92492,243.97896,540.39668,243.979,540.86798);
		context.closePath();
		context.fill();
		context.stroke();
		context.restore();
		context.restore();
		context.restore();
		context.restore();
	}

	private drawClefX (clef : MappedClef, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let previousStype = context.fillStyle;
		context.fillStyle = 'yellow';
		this.drawClefC(clef, staveConfig, context, x, y);
		context.fillStyle = previousStype;
	}

}
