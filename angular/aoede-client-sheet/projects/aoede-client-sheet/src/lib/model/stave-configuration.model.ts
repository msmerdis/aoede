export interface StaveConfiguration {
	scale            : number;
	stavesMargin     : number;
	stavesWidth      : number;
	stavesSpacing    : number;
	stavesLineHeight : number;
	stavesHalfHeight : number;
	stavesFullHeight : number;
	lineHeight       : number;
	noteSpacing      : number;
};

export function staveConfigurationInitializer (scale : number = 5, line : number = 2, space : number = 5) : StaveConfiguration {
	space *= scale;
	line  *= scale;

	return {
		scale            : scale,
		stavesMargin     :  11 * space,
		stavesWidth      : 188 * space,
		stavesSpacing    :   2 * space,
		stavesLineHeight :   2 * space + 1 * line,
		stavesHalfHeight :   4 * space + 2 * line,
		stavesFullHeight :   8 * space + 5 * line,
		lineHeight       :        line,
		noteSpacing      :       space
	};
};
