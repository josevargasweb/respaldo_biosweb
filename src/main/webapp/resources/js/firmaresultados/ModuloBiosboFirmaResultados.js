class BiolisDatatableOrdenesExtendida extends BiolisDatatableOrdenes {
    constructor(jqidDataTable, buscadorOrdenes, selectCallback, opciones = this.defaultContent()) {
      super(jqidDataTable, buscadorOrdenes, selectCallback, opciones);
    }
  
    // Métodos adicionales de la clase extendida
  
    miMetodoExtendido() {
      console.log("Este es un método extendido de la clase BiolisDatatableOrdenesExtendida.");
    }
  }