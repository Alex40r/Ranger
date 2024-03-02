### VARIABLES ###

SRC_DIR = src
BUILD_DIR = build
OUTPUT_DIR = out
DOCS_DIR = out/docs
RES_DIR = res

CLASS_PATH = $(BUILD_DIR)
JAR_NAME = ranger.jar

JC = javac
JC_FLAGS = -encoding UTF-8 -implicit:none -d $(BUILD_DIR) -cp $(CLASS_PATH)

JD = javadoc
JD_FLAGS = -encoding UTF-8 -d $(DOCS_DIR) -classpath $(CLASS_PATH) -Xmaxwarns 1000

JR = java
JR_FLAGS =

JAR = jar
JAR_FLAGS = cfe

### FAKE RULES ###

all: jar

run: jar
	$(JR) $(JR_FLAGS) -jar $(OUTPUT_DIR)/$(JAR_NAME) $(ARGS)

jar: build $(OUTPUT_DIR)/$(JAR_NAME)

clean-jar:
	rm -rf $(OUTPUT_DIR)

build: $(BUILD_DIR)/ranger/Ranger.class

quick-run:
	$(JC) $(JC_FLAGS) $(shell find $(SRC_DIR) -name "*.java")
	$(JAR) $(JAR_FLAGS) $(OUTPUT_DIR)/$(JAR_NAME) ranger.Ranger -C $(BUILD_DIR) . -C $(SRC_DIR) $(RES_DIR)
	$(JR) $(JR_FLAGS) -jar $(OUTPUT_DIR)/$(JAR_NAME) $(ARGS)

clean-build:
	rm -rf $(BUILD_DIR)

docs:
	mkdir -p $(DOCS_DIR)
	$(JD) $(JD_FLAGS) $(shell find $(SRC_DIR) -name "*.java")

clean-docs:
	rm -rf $(DOCS_DIR)

clean: clean-jar clean-build clean-docs

.PHONY: all run quick-run jar clean-jar build clean-build docs clean-docs clean

### REAL RULES ###

$(OUTPUT_DIR)/$(JAR_NAME): $(BUILD_DIR)/ranger/Ranger.class
	mkdir -p $(OUTPUT_DIR)
	$(JAR) $(JAR_FLAGS) $(OUTPUT_DIR)/$(JAR_NAME) ranger.Ranger -C $(BUILD_DIR) . -C $(SRC_DIR) $(RES_DIR)

### ranger/Ranger.class ###

$(BUILD_DIR)/ranger/Ranger.class: $(SRC_DIR)/ranger/Ranger.java \
		$(BUILD_DIR)/ranger/ui/popup/standard/ErrorPopup.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/UserInterfaceController.class \
		$(BUILD_DIR)/ranger/syntax/parser/ParserType.class \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/function/DefaultFunctionRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/Ranger.java


### ranger/ui/popup/standard/ErrorPopup.class ###

$(BUILD_DIR)/ranger/ui/popup/standard/ErrorPopup.class: $(SRC_DIR)/ranger/ui/popup/standard/ErrorPopup.java \
		$(BUILD_DIR)/ranger/ui/popup/standard/IconPopup.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/standard/ErrorPopup.java


### ranger/ui/popup/standard/IconPopup.class ###

$(BUILD_DIR)/ranger/ui/popup/standard/IconPopup.class: $(SRC_DIR)/ranger/ui/popup/standard/IconPopup.java \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/Label.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/standard/IconPopup.java


### ranger/ui/popup/Popup.class ###
### ranger/ui/popup/PopupListener.class ###

$(BUILD_DIR)/ranger/ui/popup/Popup.class $(BUILD_DIR)/ranger/ui/popup/PopupListener.class: $(SRC_DIR)/ranger/ui/popup/Popup.java $(SRC_DIR)/ranger/ui/popup/PopupListener.java \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/Popup.java $(SRC_DIR)/ranger/ui/popup/PopupListener.java


### ranger/ui/component/layout/Padding.class ###

$(BUILD_DIR)/ranger/ui/component/layout/Padding.class: $(SRC_DIR)/ranger/ui/component/layout/Padding.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/layout/Padding.java


### ranger/ui/component/Container.class ###

$(BUILD_DIR)/ranger/ui/component/Container.class: $(SRC_DIR)/ranger/ui/component/Container.java \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Container.java


### ranger/ui/component/layout/DirectionalLayout.class ###

$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class: $(SRC_DIR)/ranger/ui/component/layout/DirectionalLayout.java \
		$(BUILD_DIR)/ranger/ui/component/layout/WeightedComponent.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/layout/DirectionalLayout.java


### ranger/ui/component/layout/WeightedComponent.class ###

$(BUILD_DIR)/ranger/ui/component/layout/WeightedComponent.class: $(SRC_DIR)/ranger/ui/component/layout/WeightedComponent.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/layout/WeightedComponent.java


### ranger/ui/component/layout/Direction.class ###

$(BUILD_DIR)/ranger/ui/component/layout/Direction.class: $(SRC_DIR)/ranger/ui/component/layout/Direction.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/layout/Direction.java


### ranger/setting/SettingsRegistrar.class ###

$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class: $(SRC_DIR)/ranger/setting/SettingsRegistrar.java \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/setting/SettingsRegistrar.java


### ranger/setting/Setting.class ###

$(BUILD_DIR)/ranger/setting/Setting.class: $(SRC_DIR)/ranger/setting/Setting.java \
		$(BUILD_DIR)/ranger/syntax/parser/ParserType.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/setting/Setting.java


### ranger/syntax/parser/ParserType.class ###

$(BUILD_DIR)/ranger/syntax/parser/ParserType.class: $(SRC_DIR)/ranger/syntax/parser/ParserType.java \
		$(BUILD_DIR)/ranger/syntax/parser/PrefixParser.class \
		$(BUILD_DIR)/ranger/syntax/parser/PostfixParser.class \
		$(BUILD_DIR)/ranger/syntax/parser/InfixParser.class \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/parser/ParserType.java


### ranger/syntax/parser/PrefixParser.class ###

$(BUILD_DIR)/ranger/syntax/parser/PrefixParser.class: $(SRC_DIR)/ranger/syntax/parser/PrefixParser.java \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/OperatorNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxException.class \
		$(BUILD_DIR)/ranger/operator/OperatorUsage.class \
		$(BUILD_DIR)/ranger/operator/Operator.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/parser/PrefixParser.java


### ranger/syntax/parser/ExpressionParser.class ###

$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class: $(SRC_DIR)/ranger/syntax/parser/ExpressionParser.java \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/parser/ExpressionParser.java


### ranger/syntax/node/ExpressionNode.class ###

$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class: $(SRC_DIR)/ranger/syntax/node/ExpressionNode.java \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/ExpressionNode.java


### ranger/syntax/node/SyntaxNode.class ###

$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class: $(SRC_DIR)/ranger/syntax/node/SyntaxNode.java \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/SyntaxNode.java


### ranger/syntax/EvaluationContext.class ###

$(BUILD_DIR)/ranger/syntax/EvaluationContext.class: $(SRC_DIR)/ranger/syntax/EvaluationContext.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/EvaluationContext.java


### ranger/sheet/cell/CellCoordinates.class ###

$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class: $(SRC_DIR)/ranger/sheet/cell/CellCoordinates.java \
		$(BUILD_DIR)/ranger/data/Coordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/cell/CellCoordinates.java


### ranger/data/Coordinates.class ###

$(BUILD_DIR)/ranger/data/Coordinates.class: $(SRC_DIR)/ranger/data/Coordinates.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/data/Coordinates.java


### ranger/function/Function.class ###

$(BUILD_DIR)/ranger/function/Function.class: $(SRC_DIR)/ranger/function/Function.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/Function.java


### ranger/syntax/node/OperatorNode.class ###

$(BUILD_DIR)/ranger/syntax/node/OperatorNode.class: $(SRC_DIR)/ranger/syntax/node/OperatorNode.java \
		$(BUILD_DIR)/ranger/syntax/token/OperatorToken.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/operator/OperatorUsage.class \
		$(BUILD_DIR)/ranger/operator/Operator.class \
		$(BUILD_DIR)/ranger/Utils.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/OperatorNode.java


### ranger/syntax/token/OperatorToken.class ###

$(BUILD_DIR)/ranger/syntax/token/OperatorToken.class: $(SRC_DIR)/ranger/syntax/token/OperatorToken.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/operator/Operator.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/OperatorToken.java


### ranger/syntax/token/Token.class ###

$(BUILD_DIR)/ranger/syntax/token/Token.class: $(SRC_DIR)/ranger/syntax/token/Token.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/Token.java


### ranger/operator/Operator.class ###

$(BUILD_DIR)/ranger/operator/Operator.class: $(SRC_DIR)/ranger/operator/Operator.java \
		$(BUILD_DIR)/ranger/operator/OperatorUsage.class \
		$(BUILD_DIR)/ranger/operator/OperatorPrecedence.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/operator/Operator.java


### ranger/operator/OperatorUsage.class ###

$(BUILD_DIR)/ranger/operator/OperatorUsage.class: $(SRC_DIR)/ranger/operator/OperatorUsage.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/operator/OperatorUsage.java


### ranger/operator/OperatorPrecedence.class ###

$(BUILD_DIR)/ranger/operator/OperatorPrecedence.class: $(SRC_DIR)/ranger/operator/OperatorPrecedence.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/operator/OperatorPrecedence.java


### ranger/Utils.class ###

$(BUILD_DIR)/ranger/Utils.class: $(SRC_DIR)/ranger/Utils.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/DelimiterToken.class \
		$(BUILD_DIR)/ranger/syntax/Delimiter.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/Utils.java


### ranger/syntax/token/DelimiterToken.class ###

$(BUILD_DIR)/ranger/syntax/token/DelimiterToken.class: $(SRC_DIR)/ranger/syntax/token/DelimiterToken.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/Delimiter.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/DelimiterToken.java


### ranger/syntax/Delimiter.class ###

$(BUILD_DIR)/ranger/syntax/Delimiter.class: $(SRC_DIR)/ranger/syntax/Delimiter.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/Delimiter.java


### ranger/syntax/SyntaxException.class ###

$(BUILD_DIR)/ranger/syntax/SyntaxException.class: $(SRC_DIR)/ranger/syntax/SyntaxException.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/SyntaxException.java


### ranger/syntax/parser/PostfixParser.class ###

$(BUILD_DIR)/ranger/syntax/parser/PostfixParser.class: $(SRC_DIR)/ranger/syntax/parser/PostfixParser.java \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/OperatorNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxException.class \
		$(BUILD_DIR)/ranger/operator/OperatorUsage.class \
		$(BUILD_DIR)/ranger/operator/Operator.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/parser/PostfixParser.java


### ranger/syntax/parser/InfixParser.class ###

$(BUILD_DIR)/ranger/syntax/parser/InfixParser.class: $(SRC_DIR)/ranger/syntax/parser/InfixParser.java \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/OperatorNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxException.class \
		$(BUILD_DIR)/ranger/operator/OperatorUsage.class \
		$(BUILD_DIR)/ranger/operator/OperatorPrecedence.class \
		$(BUILD_DIR)/ranger/operator/Operator.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/parser/InfixParser.java


### ranger/ui/component/layout/Orientation.class ###

$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class: $(SRC_DIR)/ranger/ui/component/layout/Orientation.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/layout/Orientation.java


### ranger/ui/component/Spacer.class ###

$(BUILD_DIR)/ranger/ui/component/Spacer.class: $(SRC_DIR)/ranger/ui/component/Spacer.java \
		$(BUILD_DIR)/ranger/ui/component/layout/WeightedComponent.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Spacer.java


### ranger/ui/component/Label.class ###

$(BUILD_DIR)/ranger/ui/component/Label.class: $(SRC_DIR)/ranger/ui/component/Label.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Label.java


### ranger/ui/component/ColoredIcon.class ###

$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class: $(SRC_DIR)/ranger/ui/component/ColoredIcon.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/ColoredIcon.java


### ranger/ui/UserInterfaceController.class ###

$(BUILD_DIR)/ranger/ui/UserInterfaceController.class: $(SRC_DIR)/ranger/ui/UserInterfaceController.java \
		$(BUILD_DIR)/ranger/ui/window/WindowStateListener.class \
		$(BUILD_DIR)/ranger/ui/window/WindowResizeLayer.class \
		$(BUILD_DIR)/ranger/ui/window/WindowResizeController.class \
		$(BUILD_DIR)/ranger/ui/window/WindowRequestListener.class \
		$(BUILD_DIR)/ranger/ui/window/WindowDragController.class \
		$(BUILD_DIR)/ranger/ui/window/Window.class \
		$(BUILD_DIR)/ranger/ui/view/ViewStorage.class \
		$(BUILD_DIR)/ranger/ui/view/ViewRequestListener.class \
		$(BUILD_DIR)/ranger/ui/view/ViewListener.class \
		$(BUILD_DIR)/ranger/ui/view/View.class \
		$(BUILD_DIR)/ranger/ui/view/Selection.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupRequestListener.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupLayer.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupController.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/menu/Menu.class \
		$(BUILD_DIR)/ranger/ui/menu/HomeMenu.class \
		$(BUILD_DIR)/ranger/ui/menu/FileMenu.class \
		$(BUILD_DIR)/ranger/ui/element/StatusBar.class \
		$(BUILD_DIR)/ranger/ui/element/MenuBar.class \
		$(BUILD_DIR)/ranger/ui/element/InputBar.class \
		$(BUILD_DIR)/ranger/ui/element/ControlBar.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/ui/component/LayerContainer.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/ViewController.class \
		$(BUILD_DIR)/ranger/ui/SheetController.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/StorageListener.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/UserInterfaceController.java


### ranger/ui/window/WindowStateListener.class ###
### ranger/ui/window/Window.class ###

$(BUILD_DIR)/ranger/ui/window/Window.class $(BUILD_DIR)/ranger/ui/window/WindowStateListener.class: $(SRC_DIR)/ranger/ui/window/Window.java $(SRC_DIR)/ranger/ui/window/WindowStateListener.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/window/Window.java $(SRC_DIR)/ranger/ui/window/WindowStateListener.java


### ranger/ui/window/WindowResizeLayer.class ###

$(BUILD_DIR)/ranger/ui/window/WindowResizeLayer.class: $(SRC_DIR)/ranger/ui/window/WindowResizeLayer.java \
		$(BUILD_DIR)/ranger/ui/window/WindowResizeController.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/window/WindowResizeLayer.java


### ranger/ui/window/WindowResizeController.class ###

$(BUILD_DIR)/ranger/ui/window/WindowResizeController.class: $(SRC_DIR)/ranger/ui/window/WindowResizeController.java \
		$(BUILD_DIR)/ranger/ui/window/Window.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/window/WindowResizeController.java


### ranger/ui/window/WindowRequestListener.class ###

$(BUILD_DIR)/ranger/ui/window/WindowRequestListener.class: $(SRC_DIR)/ranger/ui/window/WindowRequestListener.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/window/WindowRequestListener.java


### ranger/ui/window/WindowDragController.class ###

$(BUILD_DIR)/ranger/ui/window/WindowDragController.class: $(SRC_DIR)/ranger/ui/window/WindowDragController.java \
		$(BUILD_DIR)/ranger/ui/window/Window.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/window/WindowDragController.java


### ranger/ui/view/ViewStorage.class ###

$(BUILD_DIR)/ranger/ui/view/ViewStorage.class: $(SRC_DIR)/ranger/ui/view/ViewStorage.java \
		$(BUILD_DIR)/ranger/ui/view/ViewListener.class \
		$(BUILD_DIR)/ranger/ui/view/View.class \
		$(BUILD_DIR)/ranger/ui/view/Selection.class \
		$(BUILD_DIR)/ranger/sheet/StorageListener.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/view/ViewStorage.java


### ranger/ui/view/View.class ###
### ranger/ui/view/ViewListener.class ###

$(BUILD_DIR)/ranger/ui/view/View.class $(BUILD_DIR)/ranger/ui/view/ViewListener.class: $(SRC_DIR)/ranger/ui/view/View.java $(SRC_DIR)/ranger/ui/view/ViewListener.java \
		$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class \
		$(BUILD_DIR)/ranger/ui/view/Selection.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellValue.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/SheetListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/sheet/Area.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/view/View.java $(SRC_DIR)/ranger/ui/view/ViewListener.java


### ranger/ui/view/SelectionDirection.class ###

$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class: $(SRC_DIR)/ranger/ui/view/SelectionDirection.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/view/SelectionDirection.java


### ranger/ui/view/Selection.class ###

$(BUILD_DIR)/ranger/ui/view/Selection.class: $(SRC_DIR)/ranger/ui/view/Selection.java \
		$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/view/Selection.java


### ranger/sheet/cell/CellVerticalAlignment.class ###

$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class: $(SRC_DIR)/ranger/sheet/cell/CellVerticalAlignment.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/cell/CellVerticalAlignment.java


### ranger/sheet/cell/CellError.class ###

$(BUILD_DIR)/ranger/sheet/cell/CellError.class: $(SRC_DIR)/ranger/sheet/cell/CellError.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/cell/CellError.java


### ranger/sheet/cell/CellContent.class ###

$(BUILD_DIR)/ranger/sheet/cell/CellContent.class: $(SRC_DIR)/ranger/sheet/cell/CellContent.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/cell/CellContent.java


### ranger/sheet/cell/CellHorizontalAlignment.class ###

$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class: $(SRC_DIR)/ranger/sheet/cell/CellHorizontalAlignment.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/cell/CellHorizontalAlignment.java


### ranger/syntax/node/NumberNode.class ###

$(BUILD_DIR)/ranger/syntax/node/NumberNode.class: $(SRC_DIR)/ranger/syntax/node/NumberNode.java \
		$(BUILD_DIR)/ranger/syntax/token/NumberToken.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/NumberNode.java


### ranger/syntax/token/NumberToken.class ###

$(BUILD_DIR)/ranger/syntax/token/NumberToken.class: $(SRC_DIR)/ranger/syntax/token/NumberToken.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/NumberToken.java


### ranger/syntax/lexer/Lexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/Lexer.class: $(SRC_DIR)/ranger/syntax/lexer/Lexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/ReferenceSublexer.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/OperatorSublexer.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/NumberSublexer.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/FunctionSublexer.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/DelimiterSublexer.class \
		$(BUILD_DIR)/ranger/Utils.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/Lexer.java


### ranger/syntax/lexer/sublexers/Sublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/Sublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/Sublexer.java


### ranger/syntax/lexer/sublexers/ReferenceSublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/ReferenceSublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/ReferenceSublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/ReferenceToken.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/ReferenceSublexer.java


### ranger/syntax/token/ReferenceToken.class ###

$(BUILD_DIR)/ranger/syntax/token/ReferenceToken.class: $(SRC_DIR)/ranger/syntax/token/ReferenceToken.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/ReferenceToken.java


### ranger/syntax/lexer/sublexers/OperatorSublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/OperatorSublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/OperatorSublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/OperatorToken.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class \
		$(BUILD_DIR)/ranger/operator/Operator.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/OperatorSublexer.java


### ranger/syntax/lexer/sublexers/NumberSublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/NumberSublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/NumberSublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/NumberToken.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/NumberSublexer.java


### ranger/syntax/lexer/sublexers/FunctionSublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/FunctionSublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/FunctionSublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/FunctionToken.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class \
		$(BUILD_DIR)/ranger/function/Function.class \
		$(BUILD_DIR)/ranger/Utils.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/FunctionSublexer.java


### ranger/syntax/token/FunctionToken.class ###

$(BUILD_DIR)/ranger/syntax/token/FunctionToken.class: $(SRC_DIR)/ranger/syntax/token/FunctionToken.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/token/FunctionToken.java


### ranger/syntax/lexer/sublexers/DelimiterSublexer.class ###

$(BUILD_DIR)/ranger/syntax/lexer/sublexers/DelimiterSublexer.class: $(SRC_DIR)/ranger/syntax/lexer/sublexers/DelimiterSublexer.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/token/DelimiterToken.class \
		$(BUILD_DIR)/ranger/syntax/lexer/sublexers/Sublexer.class \
		$(BUILD_DIR)/ranger/syntax/Delimiter.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/lexer/sublexers/DelimiterSublexer.java


### ranger/syntax/block/ExpressionBlock.class ###
### ranger/syntax/block/FunctionBlock.class ###

$(BUILD_DIR)/ranger/syntax/block/ExpressionBlock.class $(BUILD_DIR)/ranger/syntax/block/FunctionBlock.class: $(SRC_DIR)/ranger/syntax/block/ExpressionBlock.java $(SRC_DIR)/ranger/syntax/block/FunctionBlock.java \
		$(BUILD_DIR)/ranger/syntax/token/Token.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/FunctionNode.class \
		$(BUILD_DIR)/ranger/syntax/Delimiter.class \
		$(BUILD_DIR)/ranger/Utils.class \
		$(BUILD_DIR)/ranger/syntax/token/ReferenceToken.class \
		$(BUILD_DIR)/ranger/syntax/token/OperatorToken.class \
		$(BUILD_DIR)/ranger/syntax/token/NumberToken.class \
		$(BUILD_DIR)/ranger/syntax/token/FunctionToken.class \
		$(BUILD_DIR)/ranger/syntax/token/DelimiterToken.class \
		$(BUILD_DIR)/ranger/syntax/node/ReferenceNode.class \
		$(BUILD_DIR)/ranger/syntax/node/OperatorNode.class \
		$(BUILD_DIR)/ranger/syntax/node/NumberNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxException.class \
		$(BUILD_DIR)/ranger/Utils.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/block/ExpressionBlock.java $(SRC_DIR)/ranger/syntax/block/FunctionBlock.java


### ranger/syntax/node/ReferenceNode.class ###

$(BUILD_DIR)/ranger/syntax/node/ReferenceNode.class: $(SRC_DIR)/ranger/syntax/node/ReferenceNode.java \
		$(BUILD_DIR)/ranger/syntax/token/ReferenceToken.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/ReferenceNode.java


### ranger/syntax/node/FunctionNode.class ###

$(BUILD_DIR)/ranger/syntax/node/FunctionNode.class: $(SRC_DIR)/ranger/syntax/node/FunctionNode.java \
		$(BUILD_DIR)/ranger/syntax/token/FunctionToken.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/node/FunctionNode.java


### ranger/syntax/SyntaxTree.class ###

$(BUILD_DIR)/ranger/syntax/SyntaxTree.class: $(SRC_DIR)/ranger/syntax/SyntaxTree.java \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ReferenceNode.class \
		$(BUILD_DIR)/ranger/syntax/node/ExpressionNode.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/syntax/SyntaxTree.java


### ranger/sheet/Area.class ###

$(BUILD_DIR)/ranger/sheet/Area.class: $(SRC_DIR)/ranger/sheet/Area.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/Area.java


### ranger/function/FunctionRegistrar.class ###

$(BUILD_DIR)/ranger/function/FunctionRegistrar.class: $(SRC_DIR)/ranger/function/FunctionRegistrar.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/FunctionRegistrar.java


### ranger/data/Tree.class ###
### ranger/data/TreeIterator.class ###

$(BUILD_DIR)/ranger/data/Tree.class $(BUILD_DIR)/ranger/data/TreeIterator.class: $(SRC_DIR)/ranger/data/Tree.java $(SRC_DIR)/ranger/data/TreeIterator.java \
		$(BUILD_DIR)/ranger/data/TreeNode.class \
		$(BUILD_DIR)/ranger/data/Coordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/data/Tree.java $(SRC_DIR)/ranger/data/TreeIterator.java


### ranger/data/TreeNode.class ###

$(BUILD_DIR)/ranger/data/TreeNode.class: $(SRC_DIR)/ranger/data/TreeNode.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/data/TreeNode.java


### ranger/format/Formatter.class ###

$(BUILD_DIR)/ranger/format/Formatter.class: $(SRC_DIR)/ranger/format/Formatter.java \
		$(BUILD_DIR)/ranger/format/FormatSpecifier.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/format/Formatter.java


### ranger/format/FormatSpecifier.class ###

$(BUILD_DIR)/ranger/format/FormatSpecifier.class: $(SRC_DIR)/ranger/format/FormatSpecifier.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/format/FormatSpecifier.java


### ranger/sheet/SheetRequestListener.class ###

$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class: $(SRC_DIR)/ranger/sheet/SheetRequestListener.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/SheetRequestListener.java


### ranger/sheet/Sheet.class ###
### ranger/sheet/SheetListener.class ###
### ranger/sheet/cell/CellValue.class ###
### ranger/sheet/cell/Cell.class ###
### ranger/sheet/SheetEvaluationContext.class ###
### ranger/sheet/cell/CellStorage.class ###
### ranger/sheet/cell/CellStorageListener.class ###
### ranger/sheet/action/SheetAction.class ###

$(BUILD_DIR)/ranger/sheet/Sheet.class $(BUILD_DIR)/ranger/sheet/SheetListener.class $(BUILD_DIR)/ranger/sheet/cell/CellValue.class $(BUILD_DIR)/ranger/sheet/SheetEvaluationContext.class $(BUILD_DIR)/ranger/sheet/cell/Cell.class $(BUILD_DIR)/ranger/sheet/cell/CellStorage.class $(BUILD_DIR)/ranger/sheet/cell/CellStorageListener.class $(BUILD_DIR)/ranger/sheet/action/SheetAction.class: $(SRC_DIR)/ranger/sheet/Sheet.java $(SRC_DIR)/ranger/sheet/SheetListener.java $(SRC_DIR)/ranger/sheet/cell/CellValue.java $(SRC_DIR)/ranger/sheet/SheetEvaluationContext.java $(SRC_DIR)/ranger/sheet/cell/Cell.java $(SRC_DIR)/ranger/sheet/cell/CellStorage.java $(SRC_DIR)/ranger/sheet/cell/CellStorageListener.java $(SRC_DIR)/ranger/sheet/action/SheetAction.java \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/lexer/Lexer.class \
		$(BUILD_DIR)/ranger/syntax/block/ExpressionBlock.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxTree.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/Area.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellError.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/function/Function.class \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/syntax/node/SyntaxNode.class \
		$(BUILD_DIR)/ranger/syntax/node/NumberNode.class \
		$(BUILD_DIR)/ranger/syntax/lexer/Lexer.class \
		$(BUILD_DIR)/ranger/syntax/block/ExpressionBlock.class \
		$(BUILD_DIR)/ranger/syntax/SyntaxTree.class \
		$(BUILD_DIR)/ranger/syntax/EvaluationContext.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellError.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/format/Formatter.class \
		$(BUILD_DIR)/ranger/syntax/parser/ExpressionParser.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/Area.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/data/Tree.class \
		$(BUILD_DIR)/ranger/data/Coordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/Sheet.java $(SRC_DIR)/ranger/sheet/SheetListener.java $(SRC_DIR)/ranger/sheet/cell/CellValue.java  $(SRC_DIR)/ranger/sheet/SheetEvaluationContext.java $(SRC_DIR)/ranger/sheet/cell/Cell.java $(SRC_DIR)/ranger/sheet/cell/CellStorage.java $(SRC_DIR)/ranger/sheet/cell/CellStorageListener.java $(SRC_DIR)/ranger/sheet/action/SheetAction.java


### ranger/sheet/Storage.class ###
### ranger/sheet/StorageListener.class ###

$(BUILD_DIR)/ranger/sheet/Storage.class $(BUILD_DIR)/ranger/sheet/StorageListener.class: $(SRC_DIR)/ranger/sheet/Storage.java $(SRC_DIR)/ranger/sheet/StorageListener.java \
		$(BUILD_DIR)/ranger/sheet/Sheet.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/Storage.java $(SRC_DIR)/ranger/sheet/StorageListener.java


### ranger/ui/view/ViewRequestListener.class ###

$(BUILD_DIR)/ranger/ui/view/ViewRequestListener.class: $(SRC_DIR)/ranger/ui/view/ViewRequestListener.java \
		$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/view/ViewRequestListener.java


### ranger/ui/popup/PopupRequestListener.class ###

$(BUILD_DIR)/ranger/ui/popup/PopupRequestListener.class: $(SRC_DIR)/ranger/ui/popup/PopupRequestListener.java \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/PopupRequestListener.java


### ranger/ui/popup/PopupLayer.class ###

$(BUILD_DIR)/ranger/ui/popup/PopupLayer.class: $(SRC_DIR)/ranger/ui/popup/PopupLayer.java \
		$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/PopupLayer.java


### ranger/ui/popup/PopupController.class ###

$(BUILD_DIR)/ranger/ui/popup/PopupController.class: $(SRC_DIR)/ranger/ui/popup/PopupController.java \
		$(BUILD_DIR)/ranger/ui/popup/PopupTimeoutTask.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupListener.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/PopupController.java


### ranger/ui/popup/PopupTimeoutTask.class ###

$(BUILD_DIR)/ranger/ui/popup/PopupTimeoutTask.class: $(SRC_DIR)/ranger/ui/popup/PopupTimeoutTask.java \
		$(BUILD_DIR)/ranger/ui/popup/PopupListener.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/PopupTimeoutTask.java


### ranger/ui/menu/Menu.class ###

$(BUILD_DIR)/ranger/ui/menu/Menu.class: $(SRC_DIR)/ranger/ui/menu/Menu.java \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/menu/Menu.java


### ranger/ui/menu/HomeMenu.class ###

$(BUILD_DIR)/ranger/ui/menu/HomeMenu.class: $(SRC_DIR)/ranger/ui/menu/HomeMenu.java \
		$(BUILD_DIR)/ranger/ui/view/ViewStorage.class \
		$(BUILD_DIR)/ranger/ui/view/View.class \
		$(BUILD_DIR)/ranger/ui/view/Selection.class \
		$(BUILD_DIR)/ranger/ui/menu/Menu.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/KeybindListener.class \
		$(BUILD_DIR)/ranger/ui/component/Input.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/ui/component/Block.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/menu/HomeMenu.java


### ranger/ui/component/KeybindListener.class ###

$(BUILD_DIR)/ranger/ui/component/KeybindListener.class: $(SRC_DIR)/ranger/ui/component/KeybindListener.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/KeybindListener.java


### ranger/ui/component/Input.class ###

$(BUILD_DIR)/ranger/ui/component/Input.class: $(SRC_DIR)/ranger/ui/component/Input.java \
		$(BUILD_DIR)/ranger/ui/component/KeybindPassthrough.class \
		$(BUILD_DIR)/ranger/ui/component/KeybindListener.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Input.java


### ranger/ui/component/KeybindPassthrough.class ###

$(BUILD_DIR)/ranger/ui/component/KeybindPassthrough.class: $(SRC_DIR)/ranger/ui/component/KeybindPassthrough.java \
		$(BUILD_DIR)/ranger/ui/component/KeybindListener.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/KeybindPassthrough.java


### ranger/ui/component/Button.class ###

$(BUILD_DIR)/ranger/ui/component/Button.class: $(SRC_DIR)/ranger/ui/component/Button.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Button.java


### ranger/ui/component/Block.class ###

$(BUILD_DIR)/ranger/ui/component/Block.class: $(SRC_DIR)/ranger/ui/component/Block.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Block.java


### ranger/ui/menu/FileMenu.class ###

$(BUILD_DIR)/ranger/ui/menu/FileMenu.class: $(SRC_DIR)/ranger/ui/menu/FileMenu.java \
		$(BUILD_DIR)/ranger/ui/menu/Menu.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/menu/FileMenu.java


### ranger/ui/element/StatusBar.class ###

$(BUILD_DIR)/ranger/ui/element/StatusBar.class: $(SRC_DIR)/ranger/ui/element/StatusBar.java \
		$(BUILD_DIR)/ranger/ui/view/ViewStorage.class \
		$(BUILD_DIR)/ranger/ui/view/ViewRequestListener.class \
		$(BUILD_DIR)/ranger/ui/view/View.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupRequestListener.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/element/SheetEntry.class \
		$(BUILD_DIR)/ranger/ui/element/SheetContextEntry.class \
		$(BUILD_DIR)/ranger/ui/element/RenamePopup.class \
		$(BUILD_DIR)/ranger/ui/context/ContextMenu.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/ui/component/WeightedAdapter.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/Slider.class \
		$(BUILD_DIR)/ranger/ui/component/Label.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/ui/component/Block.class \
		$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/StatusBar.java


### ranger/ui/element/SheetEntry.class ###

$(BUILD_DIR)/ranger/ui/element/SheetEntry.class: $(SRC_DIR)/ranger/ui/element/SheetEntry.java \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/SheetEntry.java


### ranger/ui/element/SheetContextEntry.class ###

$(BUILD_DIR)/ranger/ui/element/SheetContextEntry.class: $(SRC_DIR)/ranger/ui/element/SheetContextEntry.java \
		$(BUILD_DIR)/ranger/ui/context/ContextMenu.class \
		$(BUILD_DIR)/ranger/ui/context/ContextEntry.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/SheetContextEntry.java


### ranger/ui/context/ContextMenu.class ###

$(BUILD_DIR)/ranger/ui/context/ContextMenu.class: $(SRC_DIR)/ranger/ui/context/ContextMenu.java \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/context/ContextMenu.java


### ranger/ui/context/ContextEntry.class ###

$(BUILD_DIR)/ranger/ui/context/ContextEntry.class: $(SRC_DIR)/ranger/ui/context/ContextEntry.java \
		$(BUILD_DIR)/ranger/ui/context/ContextMenu.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/context/ContextEntry.java


### ranger/ui/element/RenamePopup.class ###

$(BUILD_DIR)/ranger/ui/element/RenamePopup.class: $(SRC_DIR)/ranger/ui/element/RenamePopup.java \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/layout/DirectionalLayout.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/Label.class \
		$(BUILD_DIR)/ranger/ui/component/KeybindPassthrough.class \
		$(BUILD_DIR)/ranger/ui/component/KeybindListener.class \
		$(BUILD_DIR)/ranger/ui/component/Input.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/RenamePopup.java


### ranger/sheet/StorageRequestListener.class ###

$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class: $(SRC_DIR)/ranger/sheet/StorageRequestListener.java \
		$(BUILD_DIR)/ranger/sheet/Sheet.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/StorageRequestListener.java


### ranger/ui/component/WeightedAdapter.class ###

$(BUILD_DIR)/ranger/ui/component/WeightedAdapter.class: $(SRC_DIR)/ranger/ui/component/WeightedAdapter.java \
		$(BUILD_DIR)/ranger/ui/component/layout/WeightedComponent.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/WeightedAdapter.java


### ranger/ui/component/Slider.class ###

$(BUILD_DIR)/ranger/ui/component/Slider.class: $(SRC_DIR)/ranger/ui/component/Slider.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/Slider.java


### ranger/ui/element/MenuBar.class ###

$(BUILD_DIR)/ranger/ui/element/MenuBar.class: $(SRC_DIR)/ranger/ui/element/MenuBar.java \
		$(BUILD_DIR)/ranger/ui/menu/Menu.class \
		$(BUILD_DIR)/ranger/ui/element/MenuEntry.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Direction.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/MenuBar.java


### ranger/ui/element/MenuEntry.class ###

$(BUILD_DIR)/ranger/ui/element/MenuEntry.class: $(SRC_DIR)/ranger/ui/element/MenuEntry.java \
		$(BUILD_DIR)/ranger/ui/menu/Menu.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/MenuEntry.java


### ranger/ui/element/InputBar.class ###

$(BUILD_DIR)/ranger/ui/element/InputBar.class: $(SRC_DIR)/ranger/ui/element/InputBar.java \
		$(BUILD_DIR)/ranger/ui/view/ViewRequestListener.class \
		$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class \
		$(BUILD_DIR)/ranger/ui/popup/standard/WarningPopup.class \
		$(BUILD_DIR)/ranger/ui/popup/PopupRequestListener.class \
		$(BUILD_DIR)/ranger/ui/popup/Popup.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Padding.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/KeybindListener.class \
		$(BUILD_DIR)/ranger/ui/component/Input.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/InputBar.java


### ranger/ui/popup/standard/WarningPopup.class ###

$(BUILD_DIR)/ranger/ui/popup/standard/WarningPopup.class: $(SRC_DIR)/ranger/ui/popup/standard/WarningPopup.java \
		$(BUILD_DIR)/ranger/ui/popup/standard/IconPopup.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/popup/standard/WarningPopup.java


### ranger/ui/element/ControlBar.class ###

$(BUILD_DIR)/ranger/ui/element/ControlBar.class: $(SRC_DIR)/ranger/ui/element/ControlBar.java \
		$(BUILD_DIR)/ranger/ui/window/WindowRequestListener.class \
		$(BUILD_DIR)/ranger/ui/component/layout/Orientation.class \
		$(BUILD_DIR)/ranger/ui/component/Spacer.class \
		$(BUILD_DIR)/ranger/ui/component/Label.class \
		$(BUILD_DIR)/ranger/ui/component/Container.class \
		$(BUILD_DIR)/ranger/ui/component/ColoredIcon.class \
		$(BUILD_DIR)/ranger/ui/component/Button.class \
		$(BUILD_DIR)/ranger/sheet/StorageRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/setting/SettingsRegistrar.class \
		$(BUILD_DIR)/ranger/setting/Setting.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/element/ControlBar.java


### ranger/ui/component/LayerContainer.class ###

$(BUILD_DIR)/ranger/ui/component/LayerContainer.class: $(SRC_DIR)/ranger/ui/component/LayerContainer.java
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/component/LayerContainer.java


### ranger/ui/ViewController.class ###

$(BUILD_DIR)/ranger/ui/ViewController.class: $(SRC_DIR)/ranger/ui/ViewController.java \
		$(BUILD_DIR)/ranger/ui/view/ViewStorage.class \
		$(BUILD_DIR)/ranger/ui/view/ViewRequestListener.class \
		$(BUILD_DIR)/ranger/ui/view/View.class \
		$(BUILD_DIR)/ranger/ui/view/SelectionDirection.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/ViewController.java


### ranger/ui/SheetController.class ###

$(BUILD_DIR)/ranger/ui/SheetController.class: $(SRC_DIR)/ranger/ui/SheetController.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class \
		$(BUILD_DIR)/ranger/sheet/action/SetVerticalAlignmentAction.class \
		$(BUILD_DIR)/ranger/sheet/action/SetHorizontalAlignmentAction.class \
		$(BUILD_DIR)/ranger/sheet/action/SetFormatAction.class \
		$(BUILD_DIR)/ranger/sheet/action/SetExpressionAction.class \
		$(BUILD_DIR)/ranger/sheet/action/SetAreaAction.class \
		$(BUILD_DIR)/ranger/sheet/action/FillAreaAction.class \
		$(BUILD_DIR)/ranger/sheet/Storage.class \
		$(BUILD_DIR)/ranger/sheet/SheetRequestListener.class \
		$(BUILD_DIR)/ranger/sheet/Sheet.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/ui/SheetController.java


### ranger/sheet/action/SetVerticalAlignmentAction.class ###

$(BUILD_DIR)/ranger/sheet/action/SetVerticalAlignmentAction.class: $(SRC_DIR)/ranger/sheet/action/SetVerticalAlignmentAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellVerticalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/SetVerticalAlignmentAction.java


### ranger/sheet/action/SetHorizontalAlignmentAction.class ###

$(BUILD_DIR)/ranger/sheet/action/SetHorizontalAlignmentAction.class: $(SRC_DIR)/ranger/sheet/action/SetHorizontalAlignmentAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellHorizontalAlignment.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/SetHorizontalAlignmentAction.java


### ranger/sheet/action/SetFormatAction.class ###

$(BUILD_DIR)/ranger/sheet/action/SetFormatAction.class: $(SRC_DIR)/ranger/sheet/action/SetFormatAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/SetFormatAction.java


### ranger/sheet/action/SetExpressionAction.class ###

$(BUILD_DIR)/ranger/sheet/action/SetExpressionAction.class: $(SRC_DIR)/ranger/sheet/action/SetExpressionAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/SetExpressionAction.java


### ranger/sheet/action/SetAreaAction.class ###

$(BUILD_DIR)/ranger/sheet/action/SetAreaAction.class: $(SRC_DIR)/ranger/sheet/action/SetAreaAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/SetAreaAction.java


### ranger/sheet/action/FillAreaAction.class ###

$(BUILD_DIR)/ranger/sheet/action/FillAreaAction.class: $(SRC_DIR)/ranger/sheet/action/FillAreaAction.java \
		$(BUILD_DIR)/ranger/sheet/cell/CellStorage.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellCoordinates.class \
		$(BUILD_DIR)/ranger/sheet/cell/CellContent.class \
		$(BUILD_DIR)/ranger/sheet/action/SheetAction.class \
		$(BUILD_DIR)/ranger/sheet/Area.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/sheet/action/FillAreaAction.java


### ranger/function/DefaultFunctionRegistrar.class ###

$(BUILD_DIR)/ranger/function/DefaultFunctionRegistrar.class: $(SRC_DIR)/ranger/function/DefaultFunctionRegistrar.java \
		$(BUILD_DIR)/ranger/function/standard/SumFunction.class \
		$(BUILD_DIR)/ranger/function/standard/SqrtFunction.class \
		$(BUILD_DIR)/ranger/function/standard/PowFunction.class \
		$(BUILD_DIR)/ranger/function/standard/NowFunction.class \
		$(BUILD_DIR)/ranger/function/standard/AbsFunction.class \
		$(BUILD_DIR)/ranger/function/standard/CosFunction.class \
		$(BUILD_DIR)/ranger/function/FunctionRegistrar.class \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/DefaultFunctionRegistrar.java


### ranger/function/standard/SumFunction.class ###

$(BUILD_DIR)/ranger/function/standard/SumFunction.class: $(SRC_DIR)/ranger/function/standard/SumFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/SumFunction.java


### ranger/function/standard/SqrtFunction.class ###

$(BUILD_DIR)/ranger/function/standard/SqrtFunction.class: $(SRC_DIR)/ranger/function/standard/SqrtFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/SqrtFunction.java


### ranger/function/standard/PowFunction.class ###

$(BUILD_DIR)/ranger/function/standard/PowFunction.class: $(SRC_DIR)/ranger/function/standard/PowFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/PowFunction.java


### ranger/function/standard/NowFunction.class ###

$(BUILD_DIR)/ranger/function/standard/NowFunction.class: $(SRC_DIR)/ranger/function/standard/NowFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/NowFunction.java


### ranger/function/standard/AbsFunction.class ###

$(BUILD_DIR)/ranger/function/standard/AbsFunction.class: $(SRC_DIR)/ranger/function/standard/AbsFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/AbsFunction.java


### ranger/function/standard/CosFunction.class ###

$(BUILD_DIR)/ranger/function/standard/CosFunction.class: $(SRC_DIR)/ranger/function/standard/CosFunction.java \
		$(BUILD_DIR)/ranger/function/Function.class
	$(JC) $(JC_FLAGS) $(SRC_DIR)/ranger/function/standard/CosFunction.java

