// package provided.parser;

// import provided.JottTree;
// import provided.Token;

// import java.util.ArrayList;

// public class ParamsT implements JottTree {

//     ArrayList<JottTree> children = new ArrayList<>();

//     public ParamsT(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure {
//         this.children.add(new Expr(tokens, funcName));
//         this.children.add(new ParamsT(tokens, funcName));
//         return;
//     }

//     @Override
//     public String convertToJott() {
//         StringBuilder sb = new StringBuilder(", ");
//         for (var child : this.children) {
//             sb.append(child.convertToJott());
//         }
//         return sb.toString();
//     }

//     @Override
//     public String convertToJava(String className) {
//         return null;
//     }

//     @Override
//     public String convertToC() {
//         return null;
//     }

//     @Override
//     public String convertToPython() {
//         return null;
//     }

//     @Override
//     public boolean validateTree() {
//         for(var child : this.children) {
//             boolean result = child.validateTree();
//             if (!result)
//                 return false;
//         }
//         return true;
//     }
// }
