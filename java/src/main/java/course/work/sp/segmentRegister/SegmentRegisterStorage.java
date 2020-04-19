package course.work.sp.segmentRegister;

import course.work.sp.tokenizer.Token;
import course.work.sp.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class SegmentRegisterStorage {

    private static final String NOTHING = "Nothing";

    private static SegmentRegisterStorage instance;

    private List<SegmentRegister> segmentRegisterList;

    private SegmentRegisterStorage() {
        segmentRegisterList = new ArrayList<>();
        segmentRegisterList.add(new SegmentRegister((byte) 0x3E, TokenType.DS, NOTHING));
        segmentRegisterList.add(new SegmentRegister((byte) 0x2E, TokenType.CS, NOTHING));
        segmentRegisterList.add(new SegmentRegister((byte) 0x26, TokenType.ES, NOTHING));
        segmentRegisterList.add(new SegmentRegister((byte) 0x36, TokenType.SS, NOTHING));
        segmentRegisterList.add(new SegmentRegister((byte) 0x64, TokenType.FS, NOTHING));
        segmentRegisterList.add(new SegmentRegister((byte) 0x65, TokenType.GS, NOTHING));
    }

    private void changeDestinationByTokenType(TokenType tokenType, String destination) {
        for (SegmentRegister sg : segmentRegisterList) {
            if (sg.getTokenType().equals(tokenType)) {
                sg.setDestination(destination);
            }
        }
    }

    public static SegmentRegisterStorage getInstance() {
        if (instance == null) {
            instance = new SegmentRegisterStorage();
        }
        return instance;
    }

    public void changeRegister(List<List<Token>> fileToken) {
        boolean isAssume = false;
        List<List<Token>> operand = new ArrayList<>();
        for (List<Token> tokens : fileToken) {
            for (int tokenNumber = 0; tokenNumber < tokens.size(); tokenNumber++) {
                if (tokens.get(tokenNumber).type == TokenType.KeyWord) {
                    operand = new ArrayList<>();
                    List<Token> arrayOperand = new ArrayList<>();
                    for (int numberOfOperands = tokenNumber + 1; numberOfOperands < tokens.size(); numberOfOperands++) {
                        if (tokens.get(numberOfOperands).type != TokenType.Comma) {
                            arrayOperand.add(tokens.get(numberOfOperands));
                        } else {
                            operand.add(arrayOperand);
                            arrayOperand = new ArrayList<>();
                        }
                    }
                    operand.add(arrayOperand);
                    isAssume = true;
                }
            }
        }

        if (isAssume) {
            for (List<Token> operands : operand) {
                switch (operands.get(0).stringToken) {
                    case ("DS"):
                        changeDestinationByTokenType(TokenType.DS, operands.get(2).stringToken);
                        break;
                    case ("ES"):
                        changeDestinationByTokenType(TokenType.ES, operands.get(2).stringToken);
                        break;
                    case ("FS"):
                        changeDestinationByTokenType(TokenType.FS, operands.get(2).stringToken);
                        break;
                    case ("SS"):
                        changeDestinationByTokenType(TokenType.SS, operands.get(2).stringToken);
                        break;
                    case ("CS"):
                        changeDestinationByTokenType(TokenType.CS, operands.get(2).stringToken);
                        break;
                    case ("GS"):
                        changeDestinationByTokenType(TokenType.GS, operands.get(2).stringToken);
                        break;
                    default:
                        System.out.println("Unknown register");
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Segment Register | Destination\n");
        for (SegmentRegister sr : segmentRegisterList){
            sb.append(sr.getTokenType()).append("    ").append(sr.getDestination()).append("\n");
        }
        return sb.toString();
    }
}
