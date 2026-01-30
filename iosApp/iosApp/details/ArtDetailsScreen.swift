//
//  ArtDetailsScreen.swift
//  iosApp
//

import SwiftUI
import Shared

struct ArtDetailsScreen<VM: ArtDetailsViewModelProtocol>: View {
    @StateObject var viewModel: VM
    @Environment(\.dismiss) var dismiss
    
    @State private var scrollOffset: CGFloat = 0
    
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
                    statusBadgeText: viewModel.state.statusBadgeText
                )
                
                AboutSection(
                    artwork: artwork,
                    facts: viewModel.state.facts
                )
                
                HistorySection(artwork: artwork)
                
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
                .foregroundColor(Color(hex: 0xAFAFAF))
            
            if let badgeText = statusBadgeText {
                Text(badgeText)
                    .font(.caption)
                    .fontWeight(.medium)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 4)
                    .background(Color(hex: 0x895323))
                    .foregroundColor(Color(hex: 0x0B0B0B))
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
    let artwork: Artwork
    let facts: [KotlinPair<NSString, NSString>]
    
    @State private var isExpanded = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("About")
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(.white)
            
            let description = artwork.description.replacingOccurrences(of: "<[^>]+>", with: "", options: .regularExpression)
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
                            .foregroundColor(Color(hex: 0x895323))
                    }
                    .padding(.top, 4)
                }
            }
            
            
            Spacer().frame(height: 16)
            
            // Facts Grid
            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                ForEach(0..<facts.count, id: \.self) { index in
                    let pair = facts[index]
                    FactItem(label: pair.first as String? ?? "", value: pair.second as String? ?? "")
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
                .foregroundColor(Color(hex: 0xAFAFAF))
            Text(value)
                .font(.subheadline)
                .fontWeight(.medium)
                .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

private struct HistorySection: View {
    let artwork: Artwork
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if let exhibition = artwork.exhibitionHistory {
                ExpandableSection(title: "Exhibition History", content: exhibition)
            }
            if let publication = artwork.publicationHistory {
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
            Divider().background(Color(hex: 0x3A3A3A).opacity(0.5))
            
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
                Text(content.replacingOccurrences(of: "<[^>]+>", with: "", options: .regularExpression))
                    .font(.subheadline)
                    .foregroundColor(Color(hex: 0xAFAFAF))
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
            
            ZStack(alignment: .bottomTrailing) {
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color(hex: 0x3A3A3A).opacity(0.2))
                    .aspectRatio(1.7, contentMode: .fit)
                
                Image(systemName: "mappin.and.ellipse")
                    .font(.system(size: 40))
                    .foregroundColor(Color(hex: 0x895323))
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                
                Text("Map View Placeholder")
                    .font(.caption2)
                    .foregroundColor(Color(hex: 0xAFAFAF))
                    .padding(8)
            }
            
            HStack(spacing: 4) {
                Image(systemName: "mappin.and.ellipse")
                    .font(.system(size: 16))
                    .foregroundColor(Color(hex: 0x895323))
                
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
