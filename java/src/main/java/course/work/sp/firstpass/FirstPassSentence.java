package course.work.sp.firstpass;

import course.work.sp.fileparser.NewSentence;
import course.work.sp.fileparser.Operand;
import course.work.sp.identifierstorage.Constant;
import course.work.sp.identifierstorage.IdentifierStore;
import course.work.sp.tokenizer.TokenType;

public class FirstPassSentence {

    private int size;
    private int offset;
    private NewSentence newSentence;

    public FirstPassSentence(NewSentence newSentence, int preOffset) {
        this.newSentence = newSentence;
        for(Operand op :newSentence.getOperands()){          // change operand type from Identifier to Identifier using equals Constant type
            int index = 0;
            if(op.equalOperandType(TokenType.Identifier)){
                if(op.isSegmentReg()) index++;
                if(newSentence.getInstruction().equals(TokenType.Instruction))
                    for (Constant constant: IdentifierStore.getInstance().getConstantList())
                        if(op.getTokens().get(index).getStringToken().equals(constant.getConstant())){
                            op.setOperandType(constant.getType());
                            break;
                        }
            }
        }
        Mnem sizeSentence = new Mnem();
        size = sizeSentence.mnem(newSentence.getInstruction(), newSentence.getOperands(), newSentence.getNumber());
        if(size < 0){
            size = 0;
            this.newSentence.setError(true);
        }
        this.offset = preOffset;
        //TODO calculate size and offset
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public NewSentence getNewSentence() {
        return newSentence;
    }

    public void setNewSentence(NewSentence newSentence) {
        this.newSentence = newSentence;
    }

    @Override
    public String toString() {
        StringBuilder pass = new StringBuilder();
        pass.append(String.format("%02d", newSentence.getNumber())).append(String.format("  %08X ", offset)).append(newSentence.getLine());
                if(newSentence.isError()) pass.append(" ERROR ):");
        return pass.toString();
    }
}