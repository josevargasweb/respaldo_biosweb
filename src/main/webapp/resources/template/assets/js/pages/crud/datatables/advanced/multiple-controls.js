"use strict";
var KTDatatablesAdvancedMultipleControls = function() {

	var init = function() {
		var table = $('#kt_datatable');

		// begin first table
		table.DataTable({
			// DOM Layout settings
                        searching: false,
                        paging: false,
                        info: false,
			dom:
				"<'row py-3'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
				"<'row'<'col-sm-12 col-md-6'i><'col-sm-12 col-md-6'p>>" +
				"<'row py-3'<'col-sm-12'tr>>" +
				"<'row py-3'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
				"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>", // read more: https://datatables.net/examples/basic_init/dom.html

			columnDefs: [
				{
					targets: -1,
					title: 'Accion',
					orderable: false,
					render: function(data, type, full, meta) {
						return '\
							<a href="javascript:;" class="btn btn-sm btn-clean btn-icon" title="Delete">\
								<i class="la la-trash"></i>\
							</a>\
						';
					},
				},
				{
					width: '75px',
					targets: 7,
					render: function(data, type, full, meta) {
						var status = {
							IFI: {'title': 'IFI', 'class': ' label-light-success'},
							ELISA: {'title': 'Delivered', 'class': ' label-light-danger'},
						};
						if (typeof status[data] === 'undefined') {
							return data;
						}
						return '<span class="label label-xl font-weight-bold ' + status[data].class + ' label-inline">' + status[data].title + '</span>';
					},
				},
//				{
//					width: '75px',
//					targets: 9,
//					render: function(data, type, full, meta) {
//						var status = {
//							1: {'title': 'Online', 'state': 'danger'},
//							2: {'title': 'Retail', 'state': 'primary'},
//							3: {'title': 'Direct', 'state': 'success'},
//						};
//						if (typeof status[data] === 'undefined') {
//							return data;
//						}
//						return '<span class="label label-' + status[data].state + ' label-dot mr-2"></span>' +
//							'<span class="font-weight-bold text-' + status[data].state + '">' + status[data].title + '</span>';
//					},
//				},
			],
                        
		});
	};

	return {
		//main function to initiate the module
		init: function() {
			init();
		}
	};

}();

jQuery(document).ready(function() {
	KTDatatablesAdvancedMultipleControls.init();
});
