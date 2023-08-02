/**
 * Formset module
 * Contains logic for generating django formsets
 * @module
 */

const MAIN_BLOCK = 'dual-listbox';

const CONTAINER_ELEMENT = 'dual-listbox__container';
const AVAILABLE_ELEMENT = 'dual-listbox__available';
const EXAMEN_AVAILABLE_ELEMENT = 'dual-listbox__examen_available';
const PANEL_AVAILABLE_ELEMENT = 'dual-listbox__panel_available';
const SELECTED_ELEMENT = 'dual-listbox__selected';
const TITLE_ELEMENT = 'dual-listbox__title';
const EXAMEN_TITLE_ELEMENT = 'dual-listbox__examen_title';
const PANEL_TITLE_ELEMENT = 'dual-listbox__panel_title';

const ITEM_ELEMENT = 'dual-listbox__item';
const NON_VISUAL_ITEM_ELEMENT = 'dual-listbox__nvitem';

const BUTTONS_ELEMENT = 'dual-listbox__buttons';
const BUTTON_ELEMENT = 'dual-listbox__button';
const SEARCH_ELEMENT = 'dual-listbox__search';
const PANEL_SEARCH_ELEMENT = 'dual-listbox__searchPaneles';

const SELECTED_MODIFIER = 'dual-listbox__item--selected';

/**
 * Dual select interface allowing the user to select items from a list of provided options.
 * @class
 */
class DualListboxEnv {

  myCurrentList = "Examenes";

  toggle() {
    if (this.myCurrentList === "Examenes") {
      this.myCurrentList = "Paneles";
    }
    else {
      this.myCurrentList = "Examenes";
    }

    /*
        if (g_currentList === "Examenes") {
          g_currentList === "Paneles";
        }
        else {
          g_currentList === "Examenes";
        }
    */
    let x = null;
    let y = null;

    if (this.myCurrentList === "Examenes") {
      x = this.availableList.parentElement;
      y = this.availablePanelList.parentElement;
      this.availablePanels = this.available;
      this.available = this.availableExamenes;

    }
    else if (this.myCurrentList === "Paneles") {
      x = this.availablePanelList.parentElement;
      y = this.availableList.parentElement;
      this.availableExamenes = this.available;
      this.available = this.availablePanels;

    }
    else {
      return;
    }

    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
    if (y.style.display === "none") {
      y.style.display = "block";
    } else {
      y.style.display = "none";
    }


  }


  constructor(selector, options = {}) {
    this.setDefaults();
    this.selected = [];
    this.availableExamenes = [];
    this.availablePanels = [];
    this.available = [];
    this.paneles = [];
    if (DualListboxEnv.isDomElement(selector)) {
      this.select = selector;
    } else {
      this.select = document.querySelector(selector);
    }

    this._initOptions(options);
    this._initReusableElements();
    this.availablePanels = this.available;
    this._splitOptions($("#panelesSelect")[0].options);
    this.available = [];
    this._splitOptions(this.select.options);
    if (options.options !== undefined) {
      this._splitOptions(options.options);
    }
    this.availableExamenes = this.available;
    this._buildDualListbox(this.select.parentNode);
    this._addActions();
    console.log("Paneles : " + this.paneles.length);
    this.redraw();
  }


  /**
   * Sets the default values that can be overwritten.
   */
  setDefaults() {
    this.addEvent = null; // TODO: Remove in favor of eventListener
    this.removeEvent = null; // TODO: Remove in favor of eventListener
    this.availableTitle = 'Exámenes';
    this.availableExamenTitle = 'Exámenes';
    this.availablePanelTitle = 'Paneles';
    this.selectedTitle = 'Seleccionados';
    this.showAddButton = true;
    this.addButtonText = '>';
    this.showRemoveButton = true;
    this.removeButtonText = '<';
    this.showAddAllButton = true;
    this.addAllButtonText = '>>';
    this.showRemoveAllButton = true;
    this.removeAllButtonText = '<<';
    this.searchPlaceholder = 'Buscar';
  }



  /**
   * Add eventListener to the dualListbox element.
   *
   * @param {String} eventName
   * @param {function} callback
   */
  addEventListener(eventName, callback) {
    this.dualListbox.addEventListener(eventName, callback);
  }

  /**
   * Add the listItem to the selected list.
   *
   * @param {NodeElement} listItem
   */
  addSelected(listItem) {
    let index = this.available.indexOf(listItem);
    if (this.myCurrentList !== "Examenes") {
      this._findPanelData(listItem);
      this.available.splice(index, 1); //Elimina elemento seleccionado
      this.redraw();
    }
    else {

      if (index > -1) {
        this.available.splice(index, 1); //Elimina elemento seleccionado
        this.selected.push(listItem);//Agrega elemento
        this._selectOption(listItem.dataset.id);
        this.redraw();

        setTimeout(() => {
          let event = document.createEvent("HTMLEvents");
          event.initEvent("added", false, true);
          event.addedElement = listItem;
          this.dualListbox.dispatchEvent(event);
        }, 0);
      }
    }
  }

  /**
   * Redraws the Dual listbox content
   */
  redraw() {
    //    this.updateAvailableListbox();
    this.updateSelectedListbox();
    this.updateAvailablePanelesListbox();
    //    this.updateAvailableExamenesListbox();
    this.updateAvailableListbox();

  }

  /**
   * Removes the listItem from the selected list.
   *
   * @param {NodeElement} listItem
   */
  removeSelected(listItem) {
    let index = this.selected.indexOf(listItem);
    if (index > -1) {
      this.selected.splice(index, 1);
      this.available.push(listItem);
      this._deselectOption(listItem.dataset.id);
      this.redraw();

      setTimeout(() => {
        let event = document.createEvent("HTMLEvents");
        event.initEvent("removed", false, true);
        event.removedElement = listItem;
        this.dualListbox.dispatchEvent(event);
      }, 0);
    }
  }

  /**
   * Filters the listboxes with the given searchString.
   *
   * @param {Object} searchString
   * @param dualListbox
   */
  searchLists(searchString, dualListbox) {
    let items = dualListbox.querySelectorAll(`.${ITEM_ELEMENT}`);
    let lowerCaseSearchString = searchString.toLowerCase();

    for (let i = 0; i < items.length; i++) {
      let item = items[i];
      
      
//      if (item.textContent.toLowerCase().indexOf(lowerCaseSearchString) === -1) {
      if (item.dataset["searchid"].toLowerCase().indexOf(lowerCaseSearchString) === -1) {
        item.style.display = 'none';
      } else {
        item.style.display = 'list-item';
      }
    }
  }

  /**
   * Update the elements in the available listbox;
   */
  updateAvailableListbox() {
    if (this.myCurrentList === 'Examenes') {
      this._updateListbox(this.availableList, this.available);
    }
    else if (this.myCurrentList === 'Paneles') {
      this._updateListbox(this.availablePanelList, this.available);
    }
    else {
      this._updateListbox(this.availableList, this.available);
    }
  }

  updateAvailablePanelesListbox() {
    this._updateListbox(this.availablePanelList, this.availablePanels);
  }

  updateAvailableExamenesListbox() {
    this._updateListbox(this.availableExamenList, this.availableExamenes);
  }

  /**
   * Update the elements in the selected listbox;
   */
  updateSelectedListbox() {
    this._updateListbox(this.selectedList, this.selected);
  }

  //
  //
  // PRIVATE FUNCTIONS
  //
  //

  /**
   * Action to set all listItems to selected.
   */
  _actionAllSelected(event) {
    event.preventDefault();

    let selected = this.available.filter((item) => item.style.display !== "none");
    selected.forEach((item) => this.addSelected(item));
  }

  /**
   * Update the elements in the listbox;
   */
  _updateListbox(list, elements) {
    while (list.firstChild) {
      list.removeChild(list.firstChild);
    }

    for (let i = 0; i < elements.length; i++) {
      let listItem = elements[i];
      list.appendChild(listItem);
    }
  }

  /**
   * Action to set one listItem to selected.
   */
  _actionItemSelected(event) {
    event.preventDefault();

    let selected = this.dualListbox.querySelector(`.${SELECTED_MODIFIER}`);
    if (selected) {
      this.addSelected(selected);
    }
  }

  /**
   * Action to set all listItems to available.
   */
  _actionAllDeselected(event) {
    event.preventDefault();

    let deselected = this.selected.filter((item) => item.style.display !== "none");
    deselected.forEach((item) => this.removeSelected(item));
  }

  /**
   * Action to set one listItem to available.
   */
  _actionItemDeselected(event) {
    event.preventDefault();

    let selected = this.dualListbox.querySelector(`.${SELECTED_MODIFIER}`);
    if (selected) {
      this.removeSelected(selected);
    }
  }

  /**
   * Action when double clicked on a listItem.
   */
  _actionItemDoubleClick(listItem, event = null) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }

    if (this.selected.indexOf(listItem) > -1) {
      this.removeSelected(listItem);
    } else {
      this.addSelected(listItem);
    }
  }

  /**
   * Action when single clicked on a listItem.
   */
  _actionItemClick(listItem, dualListbox, event = null) {
    if (event) {
      event.preventDefault();
    }

    let items = dualListbox.querySelectorAll(`.${ITEM_ELEMENT}`);

    for (let i = 0; i < items.length; i++) {
      let value = items[i];
      if (value !== listItem) {
        value.classList.remove(SELECTED_MODIFIER);
      }
    }

    if (listItem.classList.contains(SELECTED_MODIFIER)) {
      listItem.classList.remove(SELECTED_MODIFIER);
    } else {
      listItem.classList.add(SELECTED_MODIFIER);
    }
  }

  /**
   * @Private
   * Adds the needed actions to the elements.
   */
  _addActions() {
    this._addButtonActions();
    this._addSearchActions();
  }

  /**
   * Adds the actions to the buttons that are created.
   */
  _addButtonActions() {
    this.add_all_button.addEventListener('click', (event) => this._actionAllSelected(event));
    this.add_button.addEventListener('click', (event) => this._actionItemSelected(event));
    this.remove_button.addEventListener('click', (event) => this._actionItemDeselected(event));
    this.remove_all_button.addEventListener('click', (event) => this._actionAllDeselected(event));
  }

  /**
   * Adds the click items to the listItem.
   *
   * @param {Object} listItem
   */
  _addClickActions(listItem) {
    listItem.addEventListener('dblclick', (event) => this._actionItemDoubleClick(listItem, event));
    listItem.addEventListener('click', (event) => this._actionItemClick(listItem, this.dualListbox, event));
    return listItem;
  }

  /**
   * @Private
   * Adds the actions to the search input.
   */
  _addSearchActions() {
    this.search_left.addEventListener('change', (event) => this.searchLists(event.target.value, this.availableList));
    this.search_left.addEventListener('keyup', (event) => this.searchLists(event.target.value, this.availableList));
    this.panel_search_left.addEventListener('change', (event) => this.searchLists(event.target.value, this.availablePanelList));
    this.panel_search_left.addEventListener('keyup', (event) => this.searchLists(event.target.value, this.availablePanelList));
    
//    this.search_right.addEventListener('change', (event) => this.searchLists(event.target.value, this.selectedList));
//    this.search_right.addEventListener('keyup', (event) => this.searchLists(event.target.value, this.selectedList));
  }

  /**
   * @Private
   * Builds the Dual listbox and makes it visible to the user.
   */
  _buildDualListbox(container) {
    this.select.style.display = 'none';

    this.dualListBoxContainer.appendChild(this._createList(this.search_left, this.availableListTitle, this.availableExamenList, true));
    this.dualListBoxContainer.appendChild(this._createList(this.panel_search_left, this.availablePanelListTitle, this.availablePanelList, true));
    this.dualListBoxContainer.appendChild(this._createList(this.search_left, this.availableListTitle, this.availableList, false));
    this.dualListBoxContainer.appendChild(this.buttons);
    this.dualListBoxContainer.appendChild(this._createList(this.search_right, this.selectedListTitle, this.selectedList));
    this.dualListbox.appendChild(this.dualListBoxContainer);

    container.insertBefore(this.dualListbox, this.select);
  }

  /**
   * Creates list with the header.
   */
  _createList(search, header, list, hidden) {
    let result = document.createElement('div');
    if (hidden === true) {
      result.setAttribute("style", "display:none;")
    }
    else {
      result.setAttribute("style", "display:block;")
    }
    if (search !== undefined) {
      result.appendChild(search);
    }
    result.appendChild(header);
    result.appendChild(list);
    return result;
  }

  /**
   * Creates the buttons to add/remove the selected item.
   */
  _createButtons() {
    this.buttons = document.createElement('div');
    this.buttons.classList.add(BUTTONS_ELEMENT);

    this.add_all_button = document.createElement('button');
    this.add_all_button.innerHTML = this.addAllButtonText;

    this.add_button = document.createElement('button');
    this.add_button.innerHTML = this.addButtonText;

    this.remove_button = document.createElement('button');
    this.remove_button.innerHTML = this.removeButtonText;

    this.remove_all_button = document.createElement('button');
    this.remove_all_button.innerHTML = this.removeAllButtonText;

    const options = {
      showAddAllButton: this.add_all_button,
      showAddButton: this.add_button,
      showRemoveButton: this.remove_button,
      showRemoveAllButton: this.remove_all_button,
    };

    for (let optionName in options) {
      if (optionName) {
        const option = this[optionName];
        const button = options[optionName];

        button.setAttribute('type', 'button');
        button.classList.add(BUTTON_ELEMENT);

        if (option) {
          this.buttons.appendChild(button);
        }
      }
    }
  }

  /**
   * @Private
   * Creates the listItem out of the option.
   */
  _createListItem(option) {
    let listItem = document.createElement('li');

    listItem.classList.add(ITEM_ELEMENT);
    let pos = option.text.search(/\|/);
    if (pos != -1) {
      listItem.innerHTML = option.text.substring(pos+1);
    }
    else{
      listItem.innerHTML = option.text;
    }
    listItem.dataset.id = option.value;
    listItem.dataset.searchid = option.text;
    

    this._addClickActions(listItem);

    return listItem;
  }

  /**
   * @Private
   * Creates the search input.
   */
  _createPanelSearchLeft() {
    this.panel_search_left = document.createElement('input');
    this.panel_search_left.classList.add(PANEL_SEARCH_ELEMENT);
    this.panel_search_left.placeholder = this.searchPlaceholder;
  }


  _createSearchLeft() {
    this.search_left = document.createElement('input');
    this.search_left.classList.add(SEARCH_ELEMENT);
    this.search_left.placeholder = this.searchPlaceholder;
  }

  /**
   * @Private
   * Creates the search input.
   */
  _createSearchRight() {
    this.search_right = document.createElement('input');
    this.search_right.classList.add(SEARCH_ELEMENT);
    this.search_right.placeholder = this.searchPlaceholder;
  }

  /**
   * @Private
   * Deselects the option with the matching value
   *
   * @param {Object} value
   */
  _deselectOption(value) {
    let options = this.select.options;

    for (let i = 0; i < options.length; i++) {
      let option = options[i];
      if (option.value === value) {
        option.selected = false;
        option.removeAttribute('selected');
      }
    }

    if (this.removeEvent) {
      this.removeEvent(value);
    }
  }

  /**
   * @Private
   * Set the option variables to this.
   */
  _initOptions(options) {
    for (let key in options) {
      if (options.hasOwnProperty(key)) {
        this[key] = options[key];
      }
    }
  }

  /**
   * @Private
   * Creates all the static elements for the Dual listbox.
   */
  _initReusableElements() {
    this.dualListbox = document.createElement('div');
    this.dualListbox.classList.add(MAIN_BLOCK);
    if (this.select.id) {
      this.dualListbox.classList.add(this.select.id);
    }

    this.dualListBoxContainer = document.createElement('div');
    this.dualListBoxContainer.classList.add(CONTAINER_ELEMENT);

    this.availableExamenList = document.createElement('ul');
    this.availableExamenList.classList.add(EXAMEN_AVAILABLE_ELEMENT);

    this.availablePanelList = document.createElement('ul');
    this.availablePanelList.classList.add(PANEL_AVAILABLE_ELEMENT);

    this.availableList = document.createElement('ul');
    this.availableList.classList.add(AVAILABLE_ELEMENT);

    this.selectedList = document.createElement('ul');
    this.selectedList.classList.add(SELECTED_ELEMENT);

    this.availableListTitle = document.createElement('div');
    this.availableListTitle.classList.add(TITLE_ELEMENT);
    this.availableListTitle.innerText = this.availableTitle;

    this.availableExamenListTitle = document.createElement('div');
    this.availableExamenListTitle.classList.add(TITLE_ELEMENT);
    this.availableExamenListTitle.innerText = this.availableExamenTitle;

    this.availablePanelListTitle = document.createElement('div');
    this.availablePanelListTitle.classList.add(TITLE_ELEMENT);
    this.availablePanelListTitle.innerText = this.availablePanelTitle;

    this.selectedListTitle = document.createElement('div');
    this.selectedListTitle.classList.add(TITLE_ELEMENT);
    this.selectedListTitle.innerText = this.selectedTitle;

    this._createButtons();
    this._createSearchLeft();
    this._createPanelSearchLeft();   
    //    this._createSearchRight();
  }

  /**
   * @Private
   * Selects the option with the matching value
   *
   * @param {Object} value
   */
  _selectOption(value) {
    let options = this.select.options;

    for (let i = 0; i < options.length; i++) {
      let option = options[i];
      if (option.value === value) {
        option.selected = true;
        option.setAttribute('selected', '');
      }
    }

    if (this.addEvent) {
      this.addEvent(value);
    }
  }

  /**
   * @Private
   * Splits the options and places them in the correct list.
   */
  _splitOptions(options) {
    for (let i = 0; i < options.length; i++) {
      let option = options[i];
      if (DualListboxEnv.isDomElement(option)) {
        this._addOptionEnv({
          text: option.innerHTML,
          value: option.value,
          selected: option.attributes.selected
        }, this.available);
      } else {
        this._addOptionEnv(option, this.available);
      }
    }
  }

  /**
   * @Private
   * Adds option to the selected of available list (depending on the data).
   */
  _addOption(option) {
    let listItem = this._createListItem(option);

    if (option.selected) {
      this.selected.push(listItem);
    } else {
      this.available.push(listItem);
    }
  }


  _addOptionEnv(option, targetList) {
    let listItem = this._createListItem(option);

    if (option.selected) {
      this.selected.push(listItem);
    } else {
      targetList.push(listItem);
    }
  }

  /**
   * @Private
   * Returns true if argument is a DOM element
   */
  static isDomElement(o) {
    return (
      typeof HTMLElement === "object" ? o instanceof HTMLElement : //DOM2
        o && typeof o === "object" && o !== null && o.nodeType === 1 && typeof o.nodeName === "string"
    );
  }


  _findPanelData(listItem) {

    let n = this.paneles.length;
    for (let i = 0; i < n; i++) {
      if (this.paneles[i].panelName === listItem.innerHTML) {
        let lstExamenes = this.paneles[i].lstPanelExamenTest;
        let m = lstExamenes.length;
        for (let j = 0; j < m; j++) {
          this._findInAvalaibleExamenes(lstExamenes[j].examenId);
        }
      }
    }
  }

  _findInAvalaibleExamenes(examenId) {

    let n = this.availableExamenes.length;
    for (let i = n - 1; i > 0; i--) {
      if (this.availableExamenes[i].dataset["id"] === examenId.toString()) {
        this.selected.push(this.availableExamenes[i]);//Agrega elemento
        this._selectOption(this.availableExamenes[i].dataset["id"]);
        this.availableExamenes.splice(i, 1);
      }
    }

  }
}

//export default DualListboxEnv;
//export { DualListboxEnv };
