//
//  ArtDetailsScreen.swift
//  iosApp
//

import SwiftUI
import Shared
import MapKit

struct ArtDetailsScreen<VM: ArtDetailsViewModelProtocol>: View {
    @StateObject var viewModel: VM
    
    @State private var scrollOffset: CGFloat = 0
    
    private var statusBadgeText: String? {
        guard let badge = viewModel.state.statusBadge else { return nil }
        if let gallery = badge.galleryTitle, !gallery.isEmpty {
            return "On View - \(gallery)"
        }
        return "On View"
    }
    
    var body: some View {
        ZStack(alignment: .top) {
            Color(hex: 0x0B0B0B).ignoresSafeArea()
            
            if viewModel.state.screenState is ScreenState.Loading {
                ProgressView()
                    .tint(Color(hex: 0x895323))
                    .frame(maxHeight: .infinity)
            } else if let artwork = viewModel.state.artwork {
                content(artwork: artwork)
            }
            
        }
        .alert("Error", isPresented: Binding(
            get: { viewModel.state.error != nil },
            set: { _ in viewModel.perform(action: ArtDetailsViewModel.ActionDismissError()) }
        )) {
            Button("Done") {
                viewModel.perform(action: ArtDetailsViewModel.ActionDismissError())
            }
        } message: {
            Text(viewModel.state.error?.message ?? "")
        }
        .preferredColorScheme(.dark)
    }
    
    @ViewBuilder
    private func content(artwork: Artwork) -> some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                ArtDisplayView(
                    artwork: artwork,
                    cornerRadius: 0,
                    shadowRadius: 0
                )
                
                PrimaryInfoBlock(
                    artwork: artwork,
                    statusBadgeText: statusBadgeText
                )
                
                AboutSection(
                    description: viewModel.state.formattedDescription,
                    facts: viewModel.state.facts
                )
                
                HistorySection(
                    history: viewModel.state.formattedHistory,
                    publication: viewModel.state.formattedPublicationHistory
                )
                
                if viewModel.state.showLocation {
                    LocationMap(artwork: artwork)
                }
                
                Spacer(minLength: 40)
            }
            .background(GeometryReader {
                Color.clear.preference(key: ViewOffsetKey.self,
                                       value: -$0.frame(in: .named("scroll")).origin.y)
            })
        }
        .coordinateSpace(name: "scroll")
        .onPreferenceChange(ViewOffsetKey.self) { scrollOffset = $0 }
        .ignoresSafeArea(edges: .top)
    }
}

private struct PrimaryInfoBlock: View {
    let artwork: Artwork
    let statusBadgeText: String?
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(artwork.date)
                .font(.headline)
                .foregroundColor(.artSeumSecondaryText)
            
            if let badgeText = statusBadgeText {
                Text(badgeText)
                    .font(.caption)
                    .fontWeight(.medium)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 4)
                    .background(Color.artSeumAccent)
                    .foregroundColor(.artSeumBackground)
                    .clipShape(RoundedRectangle(cornerRadius: 16))
            }
        }
        .padding(.horizontal, 16)
        .padding(.top, 16)
        .padding(.bottom, 8)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

private struct AboutSection: View {
    let description: String?
    let facts: [ArtDetailsViewModel.Fact]
    
    @State private var isExpanded = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            
            if let description = description {
                Text("About")
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                
                VStack(alignment: .leading, spacing: 0) {
                    Text(description)
                        .font(.body)
                        .foregroundColor(.white)
                        .lineLimit(isExpanded ? nil : 3)
                    
                    if description.count > 150 {
                        Button(action: {
                            withAnimation {
                                isExpanded.toggle()
                            }
                        }) {
                            Text(isExpanded ? "Read Less" : "Read More")
                                .font(.body)
                                .foregroundColor(.artSeumAccent)
                        }
                        .padding(.top, 4)
                    }
                }
            }
            
            
            Spacer().frame(height: 16)
            
            // Facts Grid
            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                ForEach(0..<facts.count, id: \.self) { index in
                    let fact = facts[index]
                    FactItem(label: fact.label, value: fact.value)
                }
            }
        }
        .padding(16)
    }
}

private struct FactItem: View {
    let label: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(label)
                .font(.caption)
                .foregroundColor(.artSeumSecondaryText)
            Text(value)
                .font(.subheadline)
                .fontWeight(.medium)
                .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

private struct HistorySection: View {
    let history: String?
    let publication: String?
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if let exhibition = history {
                ExpandableSection(title: "Exhibition History", content: exhibition)
            }
            if let publication = publication {
                ExpandableSection(title: "Publication History", content: publication)
            }
        }
        .padding(.horizontal, 16)
    }
}

private struct ExpandableSection: View {
    let title: String
    let content: String
    
    @State private var expanded = true
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Divider().background(Color.artSeumSeparator.opacity(0.5))
            
            Button(action: {
                withAnimation {
                    expanded.toggle()
                }
            }) {
                HStack {
                    Text(title)
                        .font(.headline)
                        .foregroundColor(.white)
                    Spacer()
                    Image(systemName: expanded ? "chevron.up" : "chevron.down")
                        .foregroundColor(.white)
                }
                .padding(.vertical, 16)
            }
            
            if expanded {
                Text(content)
                    .font(.subheadline)
                    .foregroundColor(.artSeumSecondaryText)
                    .padding(.bottom, 16)
            }
        }
    }
}

private struct LocationMap: View {
    let artwork: Artwork
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Location")
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(.white)
            
            if let lat = artwork.latitude, let lon = artwork.longitude {
                let coordinate = CLLocationCoordinate2D(latitude: lat.doubleValue, longitude: lon.doubleValue)
                
                Map(initialPosition: .region(MKCoordinateRegion(
                    center: coordinate,
                    span: MKCoordinateSpan(latitudeDelta: 0.005, longitudeDelta: 0.005)
                ))) {
                    Marker(artwork.title, coordinate: coordinate)
                        .tint(Color.artSeumAccent)
                }
                .mapStyle(.standard(elevation: .realistic))
                .clipShape(RoundedRectangle(cornerRadius: 16))
                .aspectRatio(1.7, contentMode: .fit)
            }
            
            HStack(spacing: 4) {
                Image(systemName: "mappin.and.ellipse")
                    .font(.system(size: 16))
                    .foregroundColor(.artSeumAccent)
                
                Text("\(artwork.galleryTitle ?? "Museum"), \(artwork.placeOfOrigin)")
                    .font(.subheadline)
                    .foregroundColor(.white)
            }
        }
        .padding(16)
    }
}

private struct ViewOffsetKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue = CGFloat.zero
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value += nextValue()
    }
}

extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 8) & 0xff) / 255,
            blue: Double(hex & 0xff) / 255,
            opacity: alpha
        )
    }
}


#Preview {
    ArtDetailsScreen(viewModel: DemoArtDetailsViewModelWrapper())
}
