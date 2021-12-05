package com.app.program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.app.shared.InstructionEum;
import com.app.shared.OrientationEum;
import com.app.shared.Position;

public class ParserV1 implements ParserI<TokenTypeV1> {

	private List<String> codeInstructions = new ArrayList<>();
	private Deque<Token<TokenTypeV1, Object>> tokens = new LinkedList<>();
	
	@Override
	public void uploadFile(String path) throws FileNotFoundException {
		try {
			load(codeInstructions, path);
		} catch (IOException e) {
			throw new FileNotFoundException("Path to file :"+ path);
		}
	}
	
	@Override
	public Deque<Token<TokenTypeV1, Object>> getTokens() {
		return tokens;
	}

	@Override
	public void parse() {

		TokenTypeV1 nextExpectedToken = TokenTypeV1.TKN_001_RECTANGULAR_WORKING_AREA_DIM;
		String line;
		Token<TokenTypeV1, Object> currentToken;
		String position;
		String oriantation;

		if (codeInstructions == null || codeInstructions.size() == 0) {
			throw new LexicalException("enable to find code Instructions");
		}

		line = StringUtils.stripEnd(codeInstructions.get(0), null);

		nextExpectedToken = TokenTypeV1.TKN_010_INITIAL_POSITION;

		currentToken = new Token<>(TokenTypeV1.TKN_001_RECTANGULAR_WORKING_AREA_DIM,
				parseCoordinateOFaPoint(0, line, "rectangular working area"));
		tokens.add(currentToken);

		for (int i = 1; i < codeInstructions.size(); i++) {
			line = StringUtils.stripEnd(codeInstructions.get(i), null);

			if (StringUtils.isBlank(line)) {
				continue;
			}


			if (nextExpectedToken == TokenTypeV1.TKN_010_INITIAL_POSITION) {
				position = StringUtils.substringBeforeLast(line, " ");
				currentToken = new Token<>(nextExpectedToken,
						parseCoordinateOFaPoint(i, position, "Grass Cutter Position"));
				tokens.add(currentToken);
				nextExpectedToken=TokenTypeV1.TKN_020_INITIAL_POSITION_ORIENTATION;

				oriantation = StringUtils.substringAfterLast(line, " ");
				currentToken = new Token<>(nextExpectedToken, parseOrientation(i, oriantation));
				tokens.add(currentToken);

				nextExpectedToken = TokenTypeV1.TKN_030_MOVE_INSTRUCTION;
				continue;
			}

			if (nextExpectedToken == TokenTypeV1.TKN_030_MOVE_INSTRUCTION) {
				currentToken = new Token<>(nextExpectedToken, parseInstruction(i, line));
				tokens.add(currentToken);

				nextExpectedToken = TokenTypeV1.TKN_010_INITIAL_POSITION;

				continue;
			}
			throw new LexicalException("Program is incomplete");
		}

		codeInstructions.clear();
	}

	private Position parseCoordinateOFaPoint(int lineNumber, String str, String comment) {

		if (StringUtils.split(str).length != 2 || !StringUtils.isNumericSpace(str)) {
			throw new LexicalException("enable to read " + comment + " , at line : " + lineNumber);
		}
		return new Position(NumberUtils.toInt("" + str.charAt(0)), NumberUtils.toInt("" + str.charAt(2)));
	}

	private OrientationEum parseOrientation(int lineNumber, String str) {
		if (StringUtils.length(str) != 1 || !StringUtils.isAlphanumeric(str)) {
			throw new LexicalException("enable to read Orientation, at line : " + lineNumber);
		}
		switch (str) {
		case "N":
			return OrientationEum.NORTH;
		case "W":
			return OrientationEum.WEST;
		case "E":
			return OrientationEum.EAST;
		case "S":
			return OrientationEum.SOUTH;
		default:
			throw new LexicalException("enable to read Orientation, at line : " + lineNumber);
		}
	}

	private Queue<InstructionEum> parseInstruction(final int lineNumber, String str) {
		if (!StringUtils.isAlpha(str)) {
			throw new LexicalException("enable to read Instruction, at line : " + lineNumber);
		}
		
		
		return CharBuffer.wrap(str.toCharArray())
                .chars().mapToObj(i -> (char) i).map(str1 -> {
			switch (str1) {
			case 'G':
				return InstructionEum.LEFT;
			case 'D':
				return InstructionEum.RIGHT;
			case 'A':
				return InstructionEum.MOVE;
			default:
				throw new LexicalException(
						"enable to read Instruction, at chararater [" + str1 + "], at line : " + lineNumber);
			}
		}).collect(Collectors.toCollection(LinkedList::new));
	}


}
